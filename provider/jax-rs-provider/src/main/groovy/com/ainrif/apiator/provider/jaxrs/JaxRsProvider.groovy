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
package com.ainrif.apiator.provider.jaxrs

import com.ainrif.apiator.core.model.api.ApiType
import com.ainrif.apiator.core.reflection.ContextStack
import com.ainrif.apiator.core.reflection.MethodStack
import com.ainrif.apiator.core.reflection.RUtils
import com.ainrif.apiator.core.spi.WebServiceProvider

import javax.ws.rs.*
import javax.ws.rs.core.Context
import java.lang.annotation.Annotation
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.util.function.Predicate

class JaxRsProvider implements WebServiceProvider {

    List<Class<? extends Annotation>> wsAnnotations = [HttpMethod,
                                                       POST,
                                                       GET,
                                                       PUT,
                                                       DELETE,
                                                       OPTIONS,
                                                       HEAD,
                                                       Produces,
                                                       Consumes,
                                                       Path,
                                                       PathParam,
                                                       QueryParam,
                                                       HeaderParam,
                                                       CookieParam,
                                                       FormParam,
                                                       DefaultValue,
                                                       BeanParam,
                                                       Context]

    @Override
    ContextStack getContextStack(ApiType apiClass) {
        return new JaxRsContextStack(RUtils.getAllSuperTypes(apiClass))
    }

    @Override
    List<? extends MethodStack> getMethodStacks(ContextStack contextStack) {
        def ctxStack = contextStack as JaxRsContextStack
        return RUtils.getAllMethods(contextStack.apiType, { Method m -> Modifier.isPublic(m.modifiers) } as Predicate)
                .findAll { it.value.any { method -> wsAnnotations.any { method.isAnnotationPresent(it) } } }
                .collect { signature, methods -> new JaxRsMethodStack(methods, ctxStack) }
    }
}
