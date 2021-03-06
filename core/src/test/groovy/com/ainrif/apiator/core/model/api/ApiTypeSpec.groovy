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
package com.ainrif.apiator.core.model.api

import spock.lang.Specification
import spock.lang.Unroll

import java.lang.reflect.GenericArrayType
import java.lang.reflect.ParameterizedType

@Unroll
class ApiTypeSpec extends Specification {

    def "isGeneric; #inputType"() {
        given:
        def input = new ApiType(ModelDto1.getDeclaredField(inputType).genericType)

        expect:
        input.generic == expected

        where:
        inputType                          | expected
        'intPrimitiveField'                | false
        'objectField'                      | false
        'enumField'                        | false
        'stringField'                      | false
        'setField'                         | true
        'arrayField'                       | false
        'iterableField'                    | true
        'iterableFiledWOTemplateParameter' | true
        'genericSetArrayField'             | false
        'typeVariableType'                 | false
        'typeVariableBoundedType'          | false
        'genericBounded'                   | false //todo #generic-bound need discuss compared to prev test case
    }

    def "isArray; #inputType"() {
        given:
        def input = new ApiType(ModelDto1.getDeclaredField(inputType).genericType)

        expect:
        input.array == expected

        where:
        inputType                 | expected
        'intPrimitiveField'       | false
        'objectField'             | false
        'enumField'               | false
        'stringField'             | false
        'setField'                | false
        'arrayField'              | true
        'iterableField'           | false
        'genericSetArrayField'    | true
        'typeVariableType'        | false
        'typeVariableBoundedType' | false
        'genericBounded'          | false
    }

    def "isTemplate; #inputType"() {
        given:
        def input = new ApiType(ModelDto1.getDeclaredField(inputType).genericType)

        expect:
        input.template == expected

        where:
        inputType                 | expected
        'intPrimitiveField'       | false
        'objectField'             | false
        'enumField'               | false
        'stringField'             | false
        'setField'                | false
        'arrayField'              | false
        'iterableField'           | false
        'genericSetArrayField'    | false
        'typeVariableType'        | true
        'typeVariableBoundedType' | true
        'genericBounded'          | true
    }

    def "getTemplateName; #inputType"() {
        given:
        def input = new ApiType(ModelDto1.getDeclaredField(inputType).genericType)

        expect:
        input.templateName == expected

        where:
        inputType                 | expected
        'typeVariableType'        | 'TV'
        'typeVariableBoundedType' | 'TVB'
        'genericBounded'          | 'GENERIC_BOUNDED'
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    def "getRawType; #inputType"() {
        given:
        def input = new ApiType(ModelDto1.getDeclaredField(inputType).genericType)

        expect:
        input.rawType == expected

        where:
        inputType                 | expected
        'intPrimitiveField'       | int
        'objectField'             | Object
        'enumField'               | ModelEnum
        'stringField'             | String
        'setField'                | Set
        'arrayField'              | String[]
        'iterableField'           | Iterable
        'typeVariableType'        | Object
        'typeVariableBoundedType' | Collection
        'genericBounded'          | List
    }

    def "getRawType; generic array"() {
        given:
        def input = new ApiType(ModelDto1.getDeclaredField('genericSetArrayField').genericType)

        expect:
        GenericArrayType.isAssignableFrom input.rawType
    }

    def "getRawType; w/ wildcard type #inputType"() {
        given:
        def wildcardType = ModelWithGenericBounds.WildcardDto.getDeclaredField(inputType)
                .genericType.asType(ParameterizedType)
                .actualTypeArguments[0]
        def input = new ApiType(wildcardType)

        expect:
        input.rawType == expected

        where:
        inputType                       | expected
        'fieldWithWildcard'             | Object
        'fieldWithExtendsWildcardBound' | Collection
        'fieldWithExtendsSuperBound'    | Object
    }

    def "getting generic bounds from method return type"() {
        given:
        def input = ModelWithGenericBounds.getDeclaredMethod('getWithExtendsBound')
                .genericReturnType.asType(ParameterizedType)
                .actualTypeArguments[0]
                .with { new ApiType(it) }

        expect:
        input.rawType == ModelWithGenericBounds.BoundsDto2
    }

    def "getting generic bounds from method params"() {
        given:
        def input = ModelWithGenericBounds.getDeclaredMethod(inputType, List)
                .parameters[0]
                .parameterizedType.asType(ParameterizedType)
                .actualTypeArguments[0]
                .with { new ApiType(it) }

        expect:
        input.rawType == expected

        where:
        inputType                      | expected
        'setWithExtendsWildcardBound'  | ModelWithGenericBounds.BoundsDto
        'setWithExtendsBoundFromClass' | ModelWithGenericBounds.BoundsDto
        'setWithSuperWildcardBound'    | Object
    }

    def "getComponentApiType"() {
        given:
        def input_array = new ApiType(ModelDto1.getDeclaredField('arrayField').genericType)
        def input_generic_array = new ApiType(ModelDto1.getDeclaredField('genericSetArrayField').genericType)
        def input_collection = new ApiType(ModelDto1.getDeclaredField('iterableField').genericType)

        expect:
        input_array.componentApiType.rawType == String
        input_generic_array.componentApiType.rawType == Set

        when:
        input_collection.componentApiType

        then:
        thrown(RuntimeException)
    }

    def "getActualTypeArguments; #inputType"() {
        given:
        def input = new ApiType(ModelDto2.getDeclaredField(inputType).genericType)
        def expected = [new ApiType(ModelDto1.getDeclaredField(expectedType).genericType)].collect { it.rawType }

        expect:
        input.actualParameters.collect { it.rawType } == expected

        where:
        inputType                | expectedType
        'listGEnumField'         | 'enumField'
        'listGStringField'       | 'stringField'
        'listGSetGStringField'   | 'setField'
        'listGStringArray'       | 'arrayField'
        'listGIterableGTVBField' | 'iterableField'
        'listGTVField'           | 'typeVariableType'
        'listGTVBField'          | 'typeVariableBoundedType'
        'listGenericBounded'     | 'genericBounded'
    }

    def "getActualTypeArguments; several generics"() {
        given:
        def input = new ApiType(ModelDto2.getDeclaredField('mapGSetGStringAndGTVBField').genericType)
        def expected = [new ApiType(ModelDto1.getDeclaredField('setField').genericType),
                        new ApiType(ModelDto1.getDeclaredField('typeVariableBoundedType').genericType)]

        expect:
        input.actualParameters == expected
    }

    def "getActualTypeArguments; not generic type"() {
        given:
        def input = new ApiType(ModelDto2.getDeclaredField('objectField').genericType)

        when:
        input.actualParameters

        then:
        thrown(RuntimeException)
    }

    def "_flattenArgumentTypes; #inputType"() {
        given:
        def input = new ApiType(ModelDto2.getDeclaredField(inputType).genericType)
        def expected = expectedTypes.collect { new ApiType(it).rawType }

        expect:
        def types = input.flattenArgumentTypes().findAll { !it.array }.collect { it.rawType }
        types.size() == expected.size()
        types.toSet().size() == expected.toSet().size()
        types.containsAll(expected)

        where:
        inputType                    | expectedTypes
        'objectField'                | []
        'listGEnumField'             | [ModelEnum]
        'listGStringField'           | [String]
        'listGSetGStringField'       | [Set, String]
        'listGTVField'               | [Object]
        'listGTVBField'              | [Collection]
        'listGIterableGTVBField'     | [Iterable, Collection]
        'listGSetArrayField'         | [Set, String]
        'listGStringArray'           | [String]
        'listGenericBounded'         | [List] // todo #generic-bound generic of last list
        'mapGSetGStringAndGTVBField' | [Set, String, Collection]
    }

    def "flattenArgumentTypes; w/o generic type"() {
        expect:
        new ApiType(String).flattenArgumentTypes() == []
    }
}
