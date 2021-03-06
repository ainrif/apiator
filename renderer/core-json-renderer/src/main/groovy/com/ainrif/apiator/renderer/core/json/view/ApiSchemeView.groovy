/*
 * Copyright 2014-2018 Ainrif <support@ainrif.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ainrif.apiator.renderer.core.json.view

import com.ainrif.apiator.core.model.api.ApiEndpoint
import com.ainrif.apiator.core.model.api.ApiScheme
import com.ainrif.apiator.core.model.api.ApiType
import com.ainrif.apiator.core.reflection.RUtils
import com.ainrif.apiator.doclet.javadoc.DocletInfoIndexer
import com.ainrif.apiator.renderer.core.json.CoreJsonRenderer
import com.ainrif.apiator.renderer.plugin.spi.modeltype.ModelType
import groovy.transform.Memoized

import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.util.function.Predicate

import static com.ainrif.apiator.renderer.plugin.spi.modeltype.ModelType.ENUMERATION
import static com.ainrif.apiator.renderer.plugin.spi.modeltype.ModelType.OBJECT
import static java.util.Collections.singletonList

class ApiSchemeView {
    ApiatorInfoView apiatorInfo
    Map<String, String> clientApiInfo
    List<ApiContextView> apiContexts = []
    List<ApiEnumerationView> usedEnumerations = []
    List<ApiTypeView> usedApiTypes = []

    ApiSchemeView(ApiScheme scheme, DocletInfoIndexer docInfo) {
        this.apiatorInfo = new ApiatorInfoView(scheme.apiatorInfo)
        this.clientApiInfo = RUtils.asMap(scheme.clientApiInfo)
        this.apiContexts = scheme.apiContexts
                .collect { new ApiContextView(it, docInfo?.getClassMergedInfo(it)) }
                .sort()

        def allUsedTypes = scheme.apiContexts
                .collectMany { it.apiEndpoints.collectMany { collectAllUsedTypes(it) } }
                .unique { at1, at2 -> at1.equalsIgnoreActualParams(at2) ? 0 : 1 }

        this.usedEnumerations = allUsedTypes
                .findAll { ENUMERATION == CoreJsonRenderer.getTypeByClass(it) }
                .collect { new ApiEnumerationView(it, docInfo?.getClassMergedInfo(it)) }
                .sort(true)

        this.usedApiTypes = allUsedTypes
                .findAll { OBJECT == CoreJsonRenderer.getTypeByClass(it) }
                .collect { new ApiTypeView(it, docInfo?.getClassMergedInfo(it)) }
                .sort(true)
    }


    @Memoized
    private static Set<ApiType> collectAllUsedTypes(ApiEndpoint ae) {
        Set<ApiType> types = [] // result

        // all types used in params and return type form input data to start BFS collecting
        def nextLookup = (ae.params.collect { it.type } + ae.returnTypes*.type)
        while (nextLookup) {
            // TODO katoquro: 08/05/2018 use collectMany
            def typesToLookup = nextLookup
                    .collectMany(collectApiTypesFromGenerics).toSet()
                    .collect(mapArraysToItsTypeApiType).toSet()
                    .findAll(testTypeIsNotPrimitive)

            def typesFromFields = typesToLookup
                    .findAll(testTypeIsCustomModelType)
                    .collectMany(collectApiTypesFromFields).toSet()
                    .collectMany(collectApiTypesFromGetters).toSet()
                    .collect(mapArraysToItsTypeApiType)
                    .findAll(testTypeIsNotPrimitive)
                    .minus(typesToLookup)

            types += typesToLookup
            types += typesFromFields

            nextLookup = typesFromFields.findAll { OBJECT == CoreJsonRenderer.getTypeByClass(it) || it.generic }
        }

        return types
    }

    protected static Closure<Collection<ApiType>> collectApiTypesFromGenerics = { ApiType type ->
        return (type.flattenArgumentTypes() + type).asCollection()
    }

    protected static Closure<Collection<ApiType>> collectApiTypesFromFields = { ApiType type ->
        def fromFields = RUtils.getAllDeclaredDynamicFields(findFirstNotArrayType(type), testFieldIsPublic)
                .collect { new ApiType(it.genericType) }
        return (fromFields + type).asCollection()
    }

    protected static Closure<Collection<ApiType>> collectApiTypesFromGetters = { ApiType type ->
        def rawType = findFirstNotArrayType(type)

        if (!rawType.interface && !rawType.primitive && testTypeIsCustomModelType.call(new ApiType(rawType))) {
            def typesFromGetters = RUtils.introspectProperties(rawType)
                    .findAll { it.readMethod }
                    .collect { it.readMethod.genericReturnType }
                    .collect { new ApiType(it) }

            typesFromGetters << type

            return typesFromGetters.asCollection()
        } else {
            return singletonList(type).asCollection()
        }
    }

    protected static Class<?> findFirstNotArrayType(ApiType type) {
        def targetType = type.array ? type.componentApiType.rawType : type.rawType
        while (targetType.array) {
            targetType = targetType.componentType
        }

        return targetType
    }

    protected static Closure<ApiType> mapArraysToItsTypeApiType = { ApiType type ->
        type.array ? new ApiType(findFirstNotArrayType(type)) : type
    }

    protected static Closure<Boolean> testTypeIsNotPrimitive = { ApiType type ->
        ModelType.notPrimitiveTypes.any { it == CoreJsonRenderer.getTypeByClass(type) } &&
                Object != type.rawType && Enum != type.rawType
    }

    protected static Closure<Boolean> testTypeIsCustomModelType = { ApiType type ->
        ModelType.customModelTypes.any { it == CoreJsonRenderer.getTypeByClass(type) } &&
                Object != type.rawType && Enum != type.rawType
    }

    protected static Predicate<Field> testFieldIsPublic = { Field f ->
        Modifier.isPublic(f.modifiers)
    } as Predicate
}
