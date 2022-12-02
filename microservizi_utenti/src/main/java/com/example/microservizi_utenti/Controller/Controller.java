package com.example.microservizi_utenti.Controller;

import com.example.microservizi_utenti.Entities.Cliente;
import com.example.microservizi_utenti.Entities.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class Controller {

    @Autowired
    private ClienteRepository repository;

    @GetMapping("/")
    public String helloWorld(){
        return "Hello World";
    }
    @GetMapping("/cliente")
    public String user(@AuthenticationPrincipal OAuth2User principal) {
        Cliente c = new Cliente(principal.getAttribute("given_name"),principal.getAttribute("family_name"),principal.getAttribute("email"));
        if(!repository.findAll().contains(c))
            repository.save(c);
        return "loggato come "+principal.getAttribute("name");
    }
}
