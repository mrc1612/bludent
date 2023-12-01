package com.lucfritzke.bludent.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Setter
@Getter
public class ErroDTO implements Serializable {

    private String status;
    private String mensagem;
    
    
}
