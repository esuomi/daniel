package io.induct.daniel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.common.net.MediaType;

import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Daniel {

    private final Map<MediaType, ObjectMapper> objectMappers;

    public Daniel(Map<MediaType, ObjectMapper> objectMappers, Set<Module> modules) {
        for (Map.Entry<MediaType, ObjectMapper> objectMapper : objectMappers.entrySet()) {
            configureMapper(modules, objectMapper.getValue());
        }
        this.objectMappers = objectMappers;
    }

    private ObjectMapper configureMapper(Set<Module> modules, ObjectMapper mapper) {
        for (Module module : modules) {
            mapper.registerModule(module);
        }
        mapper.registerModule(new JodaModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    public <T> T deserialize(MediaType mediaType, Class<T> aClass, InputStream inputStream) {
        ObjectMapper mapper = selectMapper(mediaType);
        try {
            return mapper.readValue(inputStream, aClass);
        } catch (IOException e) {
            throw new DeserializationException("Failed to deserialize stream to target class " + aClass, e);
        }
    }

    public <T> List<T> deserializeAll(MediaType mediaType, TypeReference<List<T>> typeReference, InputStream stream) throws DeserializationException {
        if (typeReference == null) {
            throw new DeserializationException("Cannot deserialize events without a valid TypeReference instance, given TypeReference is null");
        }
        try {
            return selectMapper(mediaType).readValue(stream, typeReference);
        } catch (Exception e) {
            throw new DeserializationException("Failed to deserialize stream to target type " + typeReference.getType(), e);
        }
    }

    public  <T> byte[] serialize(MediaType mediaType, T object) throws SerializationException {
        if (object == null) {
            throw new SerializationException(mediaType, "Cannot serialize null instance");
        }
        try {
            return selectMapper(mediaType).writeValueAsBytes(object);
        } catch (Exception e) {
            throw new SerializationException(mediaType, "Failed to serialize event", e);
        }
    }

    public byte[] serializeAll(MediaType mediaType, List<?> objects) throws SerializationException {
        if (objects == null) {
            throw new SerializationException(mediaType, "Cannot serialize null list");
        }
        try {
            return selectMapper(mediaType).writeValueAsBytes(objects);
        } catch (Exception e) {
            throw new SerializationException(mediaType, "Failed to serialize " + objects.size() + " events", e);
        }
    }

    private ObjectMapper selectMapper(MediaType mediaType) {
        if (objectMappers.containsKey(mediaType)) {
            return objectMappers.get(mediaType);
        }
        for (Map.Entry<MediaType, ObjectMapper> objectMapper : objectMappers.entrySet()) {
            if (objectMapper.getKey().is(mediaType)) {
                return objectMapper.getValue();
            }
        }
        throw new UnsupportedMediaTypeException(mediaType);
    }
}
