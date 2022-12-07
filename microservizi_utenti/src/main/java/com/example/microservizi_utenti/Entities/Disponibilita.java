package com.example.microservizi_utenti.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "disponibilita")
public class Disponibilita {

    @Id
    private String email;

    private String disponibilita;

    public Disponibilita(String email, String disponibilita) {
        this.email = email;
        this.disponibilita = disponibilita;
    }

    public Disponibilita() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(String disponibilita) {
        this.disponibilita = disponibilita;
    }

}
