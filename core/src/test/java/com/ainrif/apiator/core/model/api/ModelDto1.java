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
package com.ainrif.apiator.core.model.api;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ModelDto1<TV, TVB extends Collection, GENERIC_BOUNDED extends List<String>> {
    int intPrimitiveField;
    Object objectField;
    ModelEnum enumField;
    String stringField;
    Set<String> setField;
    String[] arrayField;
    Iterable<?> iterableField;
    Iterable iterableFiledWOTemplateParameter;
    TV typeVariableType;
    TVB typeVariableBoundedType;
    GENERIC_BOUNDED genericBounded;

    Set<String>[] genericSetArrayField;
}