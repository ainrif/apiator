/*
 * Copyright 2014-2015 Ainrif <ainrif@outlook.com>
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
package com.ainrif.apiator.writer.core.view

import com.ainrif.apiator.core.model.api.ApiContext

class ApiContextView implements Comparable<ApiContextView> {
    String name
    String apiPath
    List<ApiEndpointView> apiEndpoints = []

    ApiContextView(ApiContext context) {
        this.name = context.name
        this.apiPath = context.apiPath
        this.apiEndpoints = context.apiEndpoints.collect { new ApiEndpointView(it) }

        this.apiEndpoints.sort()
    }

    @Override
    int compareTo(ApiContextView o) {
        return apiPath.compareToIgnoreCase(o.apiPath) ?: name.compareTo(o.name)
    }
}
