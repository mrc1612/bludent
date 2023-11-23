package com.lucfritzke.bludent.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ProcedimentoDentistaId implements Serializable {


    @Column(name = "cd_dentista")
    Long id_dentista;

    @Column(name = "cd_procedimento")
    Long id_procedimento;

    /* 
    public ProcedimentoDentistaId(Long id, Long id2) {
        this.id_dentista = id;
        this.id_procedimento = id2;
    }
    */
}
