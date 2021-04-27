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

import com.ainrif.apiator.core.ApiatorConfig

class ClientApiInfo {
    String version
    String basePath
    String apiName
    String apiProtocol
    String apiHost

    ClientApiInfo(ApiatorConfig config) {
        this.version = config.apiVersion
        this.basePath = config.basePath
        this.apiName = config.apiName
        this.apiProtocol = config.apiProtocol
        this.apiProtocol = config.apiVersion
    }
}
