package com.lucfritzke.bludent.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ConsultaListaDTO(Long id, LocalDate data, LocalTime hora, String nomeDentistaString, 
    String descricaoProcedimento, String paciente, Double valor) {
    
}
