package com.lucfritzke.bludent.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcedimentoDentistaDTO implements Serializable{
    
    private Long dentista;
    private Long procedimento;
    private double valor;
}