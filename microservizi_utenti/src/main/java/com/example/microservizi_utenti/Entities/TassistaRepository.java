package com.example.microservizi_utenti.Entities;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TassistaRepository extends JpaRepository<Tassista, String> {
    public List<Tassista> findByEmail(String email);

}
