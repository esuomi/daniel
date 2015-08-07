package io.induct.daniel.examples;

import com.google.common.net.MediaType;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.induct.daniel.Daniel;
import io.induct.daniel.DanielModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Example of simple roundtrip usage.
 */
public class Roundtrip {

    private final Logger log = LoggerFactory.getLogger(Roundtrip.class);

    public static void main(String[] args) {
        new Roundtrip().run();
    }

    private void run() {
        log.info("1. Create your Guice injector as you normally would");
        Injector injector = Guice.createInjector(new DanielModule());

        log.info("2. get Daniel instance");
        Daniel daniel = injector.getInstance(Daniel.class);

        log.info("3. take serialized input and deserialize it to object");
        InputStream stream = new ByteArrayInputStream("{\"greeting\":\"hello world\"}".getBytes());
        Greeting greeting = daniel.deserialize(MediaType.JSON_UTF_8, Greeting.class, stream);
        log.info("greeting.getGreeting() = {}", greeting.getGreeting());

        log.info("4. deserialize object back to another supported format");
        byte[] reserialized = daniel.serialize(MediaType.APPLICATION_XML_UTF_8, greeting);

        assert new String(reserialized).equals("<greeting>hello world</greeting>");
    }

}
