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
package com.ainrif.apiator.core.reflection

import java.lang.annotation.Annotation

/**
 * List of classes from parent (interface/superclass) to child (implementation)
 */
abstract class ContextStack extends ArrayList<Class> {

    protected ContextStack(Collection<? extends Class> collection) {
        super(collection)
    }

    abstract String getTitle()

    abstract String getApiContextPath()

    public <T extends Annotation> List<T> getAnnotationList(Class<T> annotationClass) {
        this.findAll { it.isAnnotationPresent(annotationClass) }
                .collect { it.getAnnotation(annotationClass) }
    }
}
