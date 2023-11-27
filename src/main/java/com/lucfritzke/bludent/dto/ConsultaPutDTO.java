package com.lucfritzke.bludent.dto;

import java.time.LocalDate;
import java.time.LocalTime;


public record ConsultaPutDTO(Long id, LocalDate data, LocalTime horario,
        Long  idProcedimento, Long idDentista , Long idPaciente) {

}
