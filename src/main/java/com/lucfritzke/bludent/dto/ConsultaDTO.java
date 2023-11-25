package com.lucfritzke.bludent.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConsultaDTO implements Serializable{
    
    LocalDate data;
    LocalTime horario;
    Long idProcedimento;
    Long idDentista;
    Long idPaciente;
}
