package com.lucfritzke.bludent.exceptions;

import java.io.Serial;

public class NotFoundException extends RuntimeException {

    @Serial
    private final static long serialVersionUID = 1L;

    public NotFoundException(String message) {
        super(message);
    }
}
