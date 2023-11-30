package com.lucfritzke.bludent.exceptions;

import com.lucfritzke.bludent.dto.ErroDTO;

public class DeleteException extends RuntimeException {

    ErroDTO erro;
    
    public DeleteException(ErroDTO erro) {
        this.erro = erro;
    }

    public DeleteException() {
        
    }
    
}
