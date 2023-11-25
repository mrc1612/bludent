package com.lucfritzke.bludent.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
@Schema(name = "procedimentoDentista", description = "Entidade que armazenará os procedimentos que cada dentista esta apto a fazer")
@Entity(name = "procedimentoDentista")
public class ProcedimentoDentista {

    @EmbeddedId
    private ProcedimentoDentistaId id;

    @ManyToOne
    @MapsId("cd_dentista")
    @JoinColumn(name = "cd_dentista")
    private Dentista dentista;

    @ManyToOne
    @MapsId("cd_procedimento")
    @JoinColumn(name = "cd_procedimento")
    private Procedimento procedimento;

    @Column(nullable = false, name = "vl_procedimento")

    private double valor;
    

    public ProcedimentoDentista(Dentista d, Procedimento p, double valor2) {
        this.id = new ProcedimentoDentistaId(d.getId(), p.getId());
        this.dentista = d;
        this.procedimento = p;
        this.valor = valor2;
    }

}
