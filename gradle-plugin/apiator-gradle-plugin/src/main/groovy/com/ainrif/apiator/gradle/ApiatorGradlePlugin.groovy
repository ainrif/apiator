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


package com.ainrif.apiator.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class ApiatorGradlePlugin implements Plugin<Project> {
    @Override
    void apply(Project target) {
        target.task('apiator',
                group: 'documentation',
                description: 'Generates documentation by scanning given classpath',
                dependsOn: 'classes',
                type: ApiatorGradleTask)
    }
}