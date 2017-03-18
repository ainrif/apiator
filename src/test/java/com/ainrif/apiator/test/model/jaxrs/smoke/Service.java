/*
 * Copyright 2014-2016 Ainrif <ainrif@outlook.com>
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

import javax.ws.rs.*;
import java.lang.annotation.*;
import java.util.List;

public interface Service {

    @POST
    @Path("/create")
    Object postDtoObject(Dto1 obj);

    @GET
    @Path("/{id}")
    Object getStringDtoInImpl(@PathParam("id") String id);

    /**
     * Inherited method Level javadoc
     *
     * @param id Inherited param level javadoc
     */
    @POST
    Dto1 putStringDto(String id);

    @GET
    List<List<Dto2>> getAll();

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @HttpMethod(HttpMethod.POST)
    @Documented
    @interface CustomPOST {
    }
}
