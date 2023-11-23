package com.lucfritzke.bludent.domain;

import java.io.Serial;
import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
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
@Schema(name = "dentista", description = "Entidade Dentista")
@Entity(name = "dentista")
public class Dentista implements Serializable{


    @Serial
    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_dentista")
    protected Long id;

    @Length(min = 4, max = 100, message = "O campo nome deve conter entre 4 a 100 caracteres")
    @NotNull(message = "O campo nome não pode ser núlo")
    @NotEmpty(message = "O campo nome não pode ser vázio")
    @NotBlank(message = "O campo nome não pode estár em branco")
    @Column(name = "nm_dentista", columnDefinition = "varchar(100)")
    private String nome;
    
   
    @Column(name = "nr_registro", unique = true)
    private int registro;

    @Email
    @Column(name = "ds_email")
    private String email;
    
   // @OneToMany(mappedBy = "dentista")
   // Set<ProcedimentoDentista>  procedimentoDentista;

}
