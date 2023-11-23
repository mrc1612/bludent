package com.lucfritzke.bludent.dto;

import java.io.Serializable;
import java.util.List;

import com.lucfritzke.bludent.domain.Procedimento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcedimentoPorDentistaDTO implements Serializable{

    Long cd_dentista;
    String nm_dentista;
    List<Procedimento> procedimentos;
    
}
