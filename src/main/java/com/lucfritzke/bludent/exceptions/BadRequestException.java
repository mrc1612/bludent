package com.lucfritzke.bludent.exceptions;

import java.io.Serial;
import java.io.Serializable;

public class BadRequestException extends RuntimeException implements Serializable {

    @Serial
    private final static long serialVersionUID = 1L;

    public BadRequestException(String message) {
        super(message);
    }
}