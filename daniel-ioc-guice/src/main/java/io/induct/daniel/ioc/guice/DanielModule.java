package io.induct.daniel.ioc.guice;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk7.Jdk7Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.net.MediaType;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.Multibinder;
import io.induct.daniel.Daniel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Map;
import java.util.Set;

public class DanielModule extends AbstractModule {

    private final Logger log = LoggerFactory.getLogger(DanielModule.class);

    @Override
    protected void configure() {

        MapBinder<MediaType, ObjectMapper> objectMappers = MapBinder.newMapBinder(binder(), MediaType.class, ObjectMapper.class);

        ImmutableMap.of(
                MediaType.JSON_UTF_8, new ObjectMapper(),
                MediaType.create("application", "x-jackson-smile"), new ObjectMapper(new SmileFactory()),
                MediaType.create("application", "yaml"), new ObjectMapper(new YAMLFactory()),
                MediaType.APPLICATION_XML_UTF_8, new XmlMapper(),
                MediaType.create("application", "cbor"), new ObjectMapper(new CBORFactory())
        ).forEach((key, value) ->
                objectMappers.addBinding(key).toInstance(value)
        );

        Lists.newArrayList(
                new GuavaModule(),
                new JodaModule(),
                new AfterburnerModule(),
                new Jdk7Module(),
                new Jdk8Module()
        ).stream().collect(
                () -> Multibinder.newSetBinder(binder(), Module.class),
                (modules, module) -> modules.addBinding().toInstance(module),
                (__ignoredOnPurpose__, __doNotParallelizeThis__) -> {});

        log.info("Daniel (de)serialization helper binding complete");
    }

    @Provides
    @Singleton
    Daniel daniel(Map<MediaType, ObjectMapper> objectMappers, Set<Module> modules) {
        return new Daniel(objectMappers, modules);
    }
}
