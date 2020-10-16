package com.salcisne.androidteste.model;

import androidx.annotation.NonNull;

public class Produto {
    private int id;
    private String nome;
    private String local;
    private int quantidade;

    public Produto(String nome, String local, int quantidade) {
        this.nome = nome;
        this.local = local;
        this.quantidade = quantidade;
    }

    @NonNull
    @Override
    public String toString() {
        return "Produto: " + nome + "\r\nLocal: " + local + "\r\nQuantidade: " + quantidade;
    }
}
