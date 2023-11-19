package com.lucfritzke.bludent.domain;

import java.time.LocalDate;

public class Paciente {

    private Long cd_paciente;
    private String nm_paciente;
    private LocalDate dt_nascimento;
    private int nr_cpf;
    private String nr_telefone;

    public Paciente() {
        // Construtor vazio necessário para JPA
    }

    public Paciente(String nome, LocalDate dataNascimento, int cpf, String telefone) {
        this.nm_paciente = nome;
        this.dt_nascimento = dataNascimento;
        this.nr_cpf = cpf;
        this.nr_telefone = telefone;
    }


    public Long getCd_paciente() {
        return cd_paciente;
    }
    public void setCd_paciente(Long cd_paciente) {
        this.cd_paciente = cd_paciente;
    }
    public String getNm_paciente() {
        return nm_paciente;
    }
    public void setNm_paciente(String nm_paciente) {
        this.nm_paciente = nm_paciente;
    }
    public LocalDate getDt_nascimento() {
        return dt_nascimento;
    }
    public void setDt_nascimento(LocalDate dt_nascimento) {
        this.dt_nascimento = dt_nascimento;
    }
    public int getNr_cpf() {
        return nr_cpf;
    }
    public void setNr_cpf(int nr_cpf) {
        this.nr_cpf = nr_cpf;
    }
    public String getNr_telefone() {
        return nr_telefone;
    }
    public void setNr_telefone(String nr_telefone) {
        this.nr_telefone = nr_telefone;
    }

    
}
