package com.example.microservizi_prenotazione.Controller;

import com.example.microservizi_prenotazione.Entities.ricercaprenotazione;
import com.example.microservizi_prenotazione.Entities.ricercarepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class controllerricerca {

    @Autowired
    private ricercarepository Ricrepository;

 @GetMapping("/getRicPren")
 public List<ricercaprenotazione> getRicPren() {
     return Ricrepository.findAll();
 }

}
