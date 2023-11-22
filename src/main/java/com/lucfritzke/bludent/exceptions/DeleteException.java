package com.lucfritzke.bludent.exceptions;

import java.io.Serializable;

public class DeleteException extends RuntimeException {

    

    public DeleteException(String message) {
        super(message);
    }
    
}
