package com.lucfritzke.bludent.exceptions;

import java.io.Serial;
import java.io.Serializable;

public class NotFoundException extends RuntimeException implements Serializable {

    @Serial
    private final static long serialVersionUID = 1L;

    public NotFoundException(String message) {
        super(message);
    }
}
