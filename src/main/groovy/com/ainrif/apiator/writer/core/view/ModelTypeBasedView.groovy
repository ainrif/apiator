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

import com.ainrif.apiator.core.model.ModelType
import com.ainrif.apiator.core.model.api.ApiType

abstract class ModelTypeBasedView {
    String type
    ModelType modelType
    List<String> basedOn = []

    ModelTypeBasedView(ApiType type) {
        this.modelType = type.modelType
        this.type = ModelType.OBJECT == type.modelType ? type.rawType.name : null
        this.basedOn = type.array ?
                [type.arrayType.name] :
                type.flattenArgumentTypes().collect { it.rawType.name }
    }
}
