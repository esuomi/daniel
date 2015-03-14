package io.induct.daniel.examples;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
* @author Esko Suomi <suomi.esko@gmail.com>
* @since 22.2.2015
*/
public class Greeting {

    private final String greeting;

    @JsonCreator
    public Greeting(@JsonProperty("greeting") String greeting) {
        this.greeting = greeting;
    }

    public String getGreeting() {
        return greeting;
    }
}
