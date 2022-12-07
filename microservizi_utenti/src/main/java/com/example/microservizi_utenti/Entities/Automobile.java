package com.example.microservizi_utenti.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "automobile")
public class Automobile {
    @Id
    private String targa;

    private String descrizione;

    private int nPosti;

    private Boolean bagliaio;

    private Boolean seggiolino;

    public Automobile(String targa, String descrizione, int nPosti, Boolean bagliaio, Boolean seggiolino) {
        this.targa = targa;
        this.descrizione = descrizione;
        this.nPosti = nPosti;
        this.bagliaio = bagliaio;
        this.seggiolino = seggiolino;
    }

    public Automobile() {

    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getnPosti() {
        return nPosti;
    }

    public void setnPosti(int nPosti) {
        this.nPosti = nPosti;
    }

    public Boolean getBagliaio() {
        return bagliaio;
    }

    public void setBagliaio(Boolean bagliaio) {
        this.bagliaio = bagliaio;
    }

    public Boolean getSeggiolino() {
        return seggiolino;
    }

    public void setSeggiolino(Boolean seggiolino) {
        this.seggiolino = seggiolino;
    }

    @Override
    public String toString() {
        return "Automobile{" +
                "targa='" + targa + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", nPosti=" + nPosti +
                ", bagliaio=" + bagliaio +
                ", seggiolino=" + seggiolino +
                '}';
    }
}
