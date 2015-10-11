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
package com.ainrif.apiator.test.model.jaxrs.smoke;

import java.util.List;

import static java.util.Arrays.asList;

public class Dto1 {

    public byte[] publicByteArray;

    public Object publicObject;

    public int publicField = 42;

    private String privateField = "prvStr";

    private List<String> privateFieldWithPublicGetter = asList("prvStrWGetter");

    public String publicFieldWithPublicGetter = "pubStrWGetter";

    public String[] publicFieldWithPrivateGetter = {"pubStrWPrivateGetter", null};

    private String privateFieldWithPrivateGetter = "privateStrWPrivateGetter";

    public List<String> getPrivateFieldWithPublicGetter() {
        privateFieldWithPublicGetter.add("via getter");
        return privateFieldWithPublicGetter;
    }

    public String getPublicFieldWithPublicGetter() {
        return publicFieldWithPublicGetter + "via getter";
    }

    private String[] getPublicFieldWithPrivateGetter() {
        publicFieldWithPrivateGetter[1] = "via getter";
        return publicFieldWithPrivateGetter;
    }

    private String getPrivateFieldWithPrivateGetter() {
        return privateFieldWithPrivateGetter + "via getter";
    }
}