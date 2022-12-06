package com.example.microservizi_utenti.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Tassista {
    @Id
    private String email;

    private String nome;

    private String cognome;

    private String nPatente;

    private Date scadenza;

    private String targa;

    public String getnPatente() {
        return nPatente;
    }

    public void setnPatente(String nPatente) {
        this.nPatente = nPatente;
    }

    public Date getScadenza() {
        return scadenza;
    }

    public void setScadenza(Date scadenza) {
        this.scadenza = scadenza;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

    public Tassista(String email, String nome, String cognome) {
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
    }

    public Tassista() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
