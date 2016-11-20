/*
 * Copyright 2014-2016 Ainrif <ainrif@outlook.com>
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
package com.ainrif.apiator.core

import com.ainrif.apiator.api.ModelTypeResolver
import com.ainrif.apiator.api.Renderer
import com.ainrif.apiator.api.WebServiceProvider
import com.ainrif.apiator.api.annotation.Api

import java.lang.annotation.Annotation

class ApiatorConfig {
    String basePackage = ''
    WebServiceProvider provider = null
    Renderer renderer = null
    List<ModelTypeResolver> modelTypeResolvers = null
    String basePath = '/api'
    String apiVersion = '1.0.0-SNAPSHOT'
    Class<Annotation> apiClass = Api
    boolean debug
}
