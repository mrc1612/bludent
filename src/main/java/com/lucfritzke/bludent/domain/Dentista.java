package com.lucfritzke.bludent.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Dentista {
    @Id
    private int cd_dentista;
    private String nm_dentista;
    private int nr_registro;

    

}
