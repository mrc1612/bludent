package com.lucfritzke.bludent.domain;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
@Schema(name = "paciente", description = "Entidade Paciente")
@Entity(name ="paciente")
public class Paciente implements Serializable{

    @Serial
    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_paciente")
    private Long id;

    @Length(min = 4, max = 100, message = "O campo nome deve conter entre 4 a 100 caracteres")
    @NotNull(message = "O campo nome não pode ser núlo")
    @NotEmpty(message = "O campo nome não pode ser vázio")
    @NotBlank(message = "O campo nome não pode estár em branco")
    @Column(name = "nm_paciente", columnDefinition = "varchar(100)")
    private String nome;

    @Past(message = "A data de nascimento deve estar no passado")
    @Column(name = "dt_nascimento")
    private LocalDate dataNascimento;

    @CPF(message = "CPF inválido")
    @Column(name = "nr_cpf", columnDefinition = "varchar(14)") 
    private String cpf;
    
    @NotBlank(message = "O número de telefone não pode estar em branco")
    @Size(min = 8, max = 15, message = "O número de telefone deve ter entre 8 e 15 caracteres")
    @Pattern(regexp = "\\d+", message = "O número de telefone deve conter apenas dígitos")
    @Column(name = "nr_telefone", columnDefinition = "varchar(15)")
    private String telefone;

    
}
