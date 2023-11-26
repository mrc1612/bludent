package com.lucfritzke.bludent.domain;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "consulta")
@Table(
    name = "consulta",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"procedimento_dentista_cd_dentista", "dt_consulta", "hr_consulta"})
        
    }
)
public class Consulta implements Serializable{


    @Serial
    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_consulta")
    protected Long id;

    @Column(name = "dt_consulta")
    private LocalDate data; 

    @Column(name = "hr_consulta")
    private LocalTime horario;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(referencedColumnName = "cd_dentista"),
        @JoinColumn(referencedColumnName = "cd_procedimento")
    })
    private ProcedimentoDentista procedimentoDentista;

    @ManyToOne
    @JoinColumn(name = "cd_paciente")
    private Paciente paciente;


    
}
