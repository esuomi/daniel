package io.induct.daniel.examples;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.induct.daniel.Daniel;
import io.induct.daniel.ioc.guice.DanielModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Esko Suomi <suomi.esko@gmail.com>
 * @since 22.2.2015
 */
public class Configurability {

    private final static Logger log = LoggerFactory.getLogger(Configurability.class);

    public static void main(String[] args) {
        log.info("1. Create your Guice injector as you normally would");
        Injector injector = Guice.createInjector(new DanielModule());

        log.info("2. get Daniel instance");
        Daniel daniel = injector.getInstance(Daniel.class);

        log.info("3. control pretty printing with a boolean");

        log.info("4. deserialize object back to another supported format");
        Greeting greeting = new Greeting("hallo!");
        boolean prettyPrint = true;

        String prettyJson = new String(daniel.serialize(greeting, prettyPrint));

        assert prettyJson.equals(
                "{" +
                "  \"greeting\" : \"hallo!\"" +
                "}");
    }

}
