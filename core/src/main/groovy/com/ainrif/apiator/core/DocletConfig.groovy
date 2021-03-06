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

package com.ainrif.apiator.core

/**
 * Config for javadoc processor
 */
class DocletConfig {
    /**
     * if {@code false} Apiator skips doc comments parsing
     */
    boolean enabled = true

    /**
     * Path to sources separated with colons (:) or semicolon for Windows OS (;)
     *
     * If empty then Apiator will try to detect it by itself
     */
    String sourcePath

    // TODO katoquro: 23/06/19 think about base package from main config usage
    /**
     * Base packages which will be scanned recursively to collect additional information from doc comments
     * If there are more than 1 package the must be divided via column (:)
     *
     * Required if doclet enabled
     */
    String includeBasePackage
}
