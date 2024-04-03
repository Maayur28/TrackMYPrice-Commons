package io.github.maayur28.exceptions;

import java.io.Serial;

public class InvalidURLException extends RuntimeException {
    /**
     * serialVersionUID
     */
    @Serial
    private static final long serialVersionUID = 7933823177423584791L;

    public InvalidURLException(String message) {
        super(message);
    }
}
