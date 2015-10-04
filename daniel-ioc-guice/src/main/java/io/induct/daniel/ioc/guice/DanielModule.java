package io.induct.daniel.ioc.guice;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
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

        objectMappers.addBinding(MediaType.JSON_UTF_8)
                .toInstance(new ObjectMapper());

        objectMappers.addBinding(MediaType.create("application", "x-jackson-smile"))
                .toInstance(new ObjectMapper(new SmileFactory()));

        objectMappers.addBinding(MediaType.create("application", "yaml"))
                .toInstance(new ObjectMapper(new YAMLFactory()));

        objectMappers.addBinding(MediaType.APPLICATION_XML_UTF_8)
                .toInstance(new XmlMapper());

        Multibinder<Module> jacksonModules = Multibinder.newSetBinder(binder(), Module.class);
        jacksonModules.addBinding().toInstance(new GuavaModule());
        jacksonModules.addBinding().toInstance(new JodaModule());

        log.info("Daniel (de)serialization helper binding complete");
    }

    @Provides
    @Singleton
    Daniel daniel(Map<MediaType, ObjectMapper> objectMappers, Set<Module> modules) {
        return new Daniel(objectMappers, modules);
    }
}
