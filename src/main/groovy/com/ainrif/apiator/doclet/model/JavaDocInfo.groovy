/*
 * Copyright 2014-2016 Ainrif <support@ainrif.com>
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

package com.ainrif.apiator.doclet.model

class JavaDocInfo {
    Map<String, ClassInfo> classes

    public static String createKey(ClassInfo info) {
        return info.name
    }

    public static String createKey(FieldInfo info) {
        return info.name
    }

    public static String createKey(MethodInfo info) {
        return "${info.name}_${info.paramTypeNames}".toString()
    }

    public static String createKey(ParamInfo info) {
        return info.name
    }
}
