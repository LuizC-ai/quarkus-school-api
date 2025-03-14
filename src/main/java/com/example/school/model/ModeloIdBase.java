package com.example.school.model;

public class ModeloIdBase {

    private Long id;

    protected String definirPrefixo() {
        return "ID";
    }

    public String gerarId() {
        return definirPrefixo() + "-" + System.currentTimeMillis();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
