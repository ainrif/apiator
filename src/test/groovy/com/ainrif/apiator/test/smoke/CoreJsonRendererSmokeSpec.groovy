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
package com.ainrif.apiator.test.smoke

import com.ainrif.apiator.core.ApiatorConfig
import com.ainrif.apiator.core.DocletConfig
import com.ainrif.apiator.doclet.SourcePathDetector
import com.ainrif.apiator.provider.jaxrs.JaxRsProvider
import com.ainrif.apiator.renderer.core.json.CoreJsonRenderer
import com.ainrif.apiator.renderer.plugin.jaxrs.JaxRsCompositePlugin
import com.ainrif.apiator.test.TestingApiator
import groovy.json.JsonSlurper
import groovy.transform.Memoized
import spock.lang.Specification

import java.nio.file.Paths

import static org.apache.commons.lang3.StringUtils.deleteWhitespace

class CoreJsonRendererSmokeSpec extends Specification {
    static final String smokeJson = CoreJsonRendererSmokeSpec.classLoader.getResource('common-on-jaxrs-smoke.json').text

    @Memoized
    static String buildSourcePath() {
        return [
                Paths.get(System.getProperty('user.dir'), 'test', 'jax-rs-model-test', 'src', 'main', 'java'),
                Paths.get(System.getProperty('user.dir'), 'test', 'core-model-test', 'src', 'main', 'java')
        ].join(SourcePathDetector.OS_PATH_DELIMITER)
    }

    ApiatorConfig getConfigWithJsonRenderer() {
        return new ApiatorConfig(
                provider: new JaxRsProvider(),
                docletConfig: new DocletConfig(
                        sourcePath: buildSourcePath(),
                        includeBasePackage: 'com.ainrif'
                ),
                renderer: new CoreJsonRenderer({
                    plugins << new JaxRsCompositePlugin()
                }),
                basePackage: 'com.ainrif.apiator.test.model.common_on_jaxrs.smoke',
        )
    }

    def "fully configured renderer"() {
        when:
        def actual = new TestingApiator(configWithJsonRenderer).render()

        then:
        new JsonSlurper().parseText(actual) == new JsonSlurper().parseText(smokeJson)
    }

    def "auto-detection of source paths"() {
        when:
        def config = configWithJsonRenderer
        config.docletConfig.sourcePath = null
        def actual = new TestingApiator(config).render()

        then:
        new JsonSlurper().parseText(actual) == new JsonSlurper().parseText(smokeJson)
    }

    def "renderer should produce the same result each time"() {
        given:
        def apiator1 = new TestingApiator(configWithJsonRenderer)
        def apiator2 = new TestingApiator(configWithJsonRenderer)
        def apiator3 = new TestingApiator(configWithJsonRenderer)

        when:
        def render1 = apiator1.render()
        def render2 = apiator2.render()
        def render3 = apiator3.render()

        then:
        render1 == render2
        render2 == render3
        deleteWhitespace(render3) == deleteWhitespace(smokeJson)
    }
}
