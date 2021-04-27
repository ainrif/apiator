package com.ainrif.apiator.renderer.postman.model_2_1

import com.ainrif.apiator.renderer.postman.model_2_1.common.Description
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue


/**
 * Using variables in your Postman requests eliminates the need to duplicate requests, which can save a lot of time. Variables can be defined, and referenced to from any part of a request.
 */
class Variable {

    /**
     * A variable ID is a unique user-defined value that identifies the variable within a collection. In traditional terms, this would be a variable name.
     */
    public String id

    /**
     * A variable key is a human friendly value that identifies the variable within a collection. In traditional terms, this would be a variable name.
     */
    public String key

    /**
     * The value that a variable holds in this collection. Ultimately, the variables will be replaced by this value, when say running a set of requests from a collection
     */
    public Object value

    /**
     * A variable may have multiple types. This field specifies the type of the variable.
     */
    public Type type

    /**
     * Variable name
     */
    public String name

    /**
     * A Description can be a raw text, or be an object, which holds the description along with its format.
     */
    public Description description

    /**
     * When set to true, indicates that this variable has been set by Postman
     */
    public boolean system = false
    public boolean disabled = false


    /**
     * A variable may have multiple types. This field specifies the type of the variable.
     */
    enum Type {
        STRING("string"),
        BOOLEAN("boolean"),
        ANY("any"),
        NUMBER("number");

        @JsonValue
        final String value

        Type(String value) {
            this.value = value
        }

        @JsonCreator
        static Type fromValue(String value) {
            return valueOf(value.toUpperCase())
        }
    }

}
