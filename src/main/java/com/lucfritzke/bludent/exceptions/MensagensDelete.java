package com.lucfritzke.bludent.exceptions;

public enum MensagensDelete {

    OK_DENTISTA("{ \"status\" : \"OK\"; \"mensagem\" : \"OK\"}"),
    ERRO_NAO_ENCONTRADO("{ \"status\" : \"ERRO\"; \"mensagem\" : \"Código de Dentista não existe\"}");

    private final String mensagem;

    MensagensDelete(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    
}
