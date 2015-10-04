package io.induct.daniel;

import com.google.common.net.MediaType;

public class UnsupportedMediaTypeException extends DanielException {
    public UnsupportedMediaTypeException(MediaType mediaType) {
        super("Unsupported media type '" + mediaType + "'");
    }
}
