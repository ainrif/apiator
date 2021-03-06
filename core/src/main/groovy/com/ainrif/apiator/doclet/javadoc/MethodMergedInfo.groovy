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

package com.ainrif.apiator.doclet.javadoc

import com.ainrif.apiator.core.model.api.ApiEndpointParam
import com.ainrif.apiator.doclet.model.MethodInfo
import com.ainrif.apiator.doclet.model.ParamInfo
import groovy.transform.CompileDynamic

import javax.annotation.Nullable

@CompileDynamic
class MethodMergedInfo {
    private List<MethodInfo> methodInfos

    MethodMergedInfo(List<MethodInfo> methodInfos) {
        this.methodInfos = methodInfos
    }

    @Nullable String getName() {
        return methodInfos.findResult { it?.name }
    }

    @Nullable String getDescription() {
        return methodInfos.findResult { it?.description }
    }

    @Nullable ParamMergedInfo getParamMergedInfo(ApiEndpointParam apiEndpointParam) {
        def paramHierarchy = methodInfos
                .collect { it.params[ParamInfo.createKey(apiEndpointParam)] }
                .findAll Objects.&nonNull

        return paramHierarchy ?
                new ParamMergedInfo(paramHierarchy) :
                null
    }
}
