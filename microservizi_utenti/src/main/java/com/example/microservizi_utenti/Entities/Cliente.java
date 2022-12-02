package com.example.microservizi_utenti.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Cliente {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;

    public Cliente(String nome, String cognome, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

    public Cliente() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String cognome;
    private String email;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}