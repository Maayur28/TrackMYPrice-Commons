package io.github.maayur28.exceptions;

import java.io.Serial;

public class DBProcessingException extends RuntimeException {
    /**
     * serialVersionUID
     */
    @Serial
    private static final long serialVersionUID = 7933823177423584791L;

    public DBProcessingException(String message) {
        super(message);
    }
}
