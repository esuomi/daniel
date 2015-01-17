package io.induct.daniel;

import com.google.common.net.MediaType;

public class SerializationException extends DanielException {

    private final MediaType mediaType;

    public SerializationException(MediaType mediaType, String message) {
        super(message);
        this.mediaType = mediaType;
    }

    public SerializationException(MediaType mediaType, String message, Throwable cause) {
        super(message, cause);
        this.mediaType = mediaType;
    }

    public SerializationException(Throwable cause, MediaType mediaType) {
        super(cause);
        this.mediaType = mediaType;
    }

    public SerializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, MediaType mediaType) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.mediaType = mediaType;
    }

    public MediaType getMediaType() {
        return mediaType;
    }
}
