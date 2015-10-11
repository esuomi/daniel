package io.induct.daniel;

public class DanielException extends RuntimeException {
    public DanielException(String message) {
        super(message);
    }

    public DanielException(String message, Throwable cause) {
        super(message, cause);
    }

    public DanielException(Throwable cause) {
        super(cause);
    }

    public DanielException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
