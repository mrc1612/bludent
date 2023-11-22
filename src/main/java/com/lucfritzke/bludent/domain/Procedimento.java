package com.lucfritzke.bludent.domain;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
@Schema(name = "procedimento", description = "Entidade procedimento")
@Entity(name ="procedimento")
public class Procedimento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_procedimento")
    private Long id;

    @Length(min = 4, max = 100, message = "O campo nome deve conter entre 4 a 100 caracteres")
    @NotNull(message = "O campo nome não pode ser núlo")
    @NotEmpty(message = "O campo nome não pode ser vázio")
    @NotBlank(message = "O campo nome não pode estár em branco")
    @Column(name = "ds_procedimento", columnDefinition = "varchar(100)")
    private String descricao;


    
}
