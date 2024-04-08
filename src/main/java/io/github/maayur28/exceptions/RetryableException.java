package io.github.maayur28.exceptions;

import java.io.Serial;

public class RetryableException extends Exception {
    /**
     * serialVersionUID
     */
    @Serial
    private static final long serialVersionUID = 7933823177423584791L;

    public RetryableException(String message) {
        super(message);
    }

    public RetryableException(String message, Throwable cause) {
        super(message, cause);
    }
}
