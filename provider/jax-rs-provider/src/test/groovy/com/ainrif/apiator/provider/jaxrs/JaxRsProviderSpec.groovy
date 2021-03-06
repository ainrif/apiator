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
import com.ainrif.apiator.test.model.jaxrs.smoke.JaxRsController
import com.ainrif.apiator.test.model.jaxrs.smoke.JaxRsControllerInheritance
import spock.lang.Specification

class JaxRsProviderSpec extends Specification {
    def "method stack should be generated for jax-rs type context"() {
        given:
        def contextStack = new JaxRsContextStack([JaxRsController, JaxRsControllerInheritance].collect {
            new ApiType(it)
        })
        def provider = new JaxRsProvider()
        and:
        String[] expected = ['post',
                             'get',
                             'put',
                             'delete',
                             'options',
                             'head',
                             'checkParams',
                             'fromChildClass']

        when:
        def actual = provider.getMethodStacks(contextStack)

        then:
        actual.size() == expected.size()
        actual.collect { it.name }.containsAll(expected)
    }
}
