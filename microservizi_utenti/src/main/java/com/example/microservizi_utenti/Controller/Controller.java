package com.example.microservizi_utenti.Controller;

import com.example.microservizi_utenti.Entities.Cliente;
import com.example.microservizi_utenti.Entities.ClienteRepository;
import com.example.microservizi_utenti.Entities.Tassista;
import com.example.microservizi_utenti.Entities.TassistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class Controller {

    @Autowired
    private ClienteRepository Clirepository;

    @Autowired
    private TassistaRepository Tasrepository;

    @GetMapping("/")
    public String helloWorld(){
        return "Hello World";
    }
    @GetMapping("/cliente")
    public String cliente(@AuthenticationPrincipal OAuth2User principal) {
        Cliente c = new Cliente(principal.getAttribute("email"),principal.getAttribute("given_name"),principal.getAttribute("family_name"));
        try{
            if(!Clirepository.findAll().contains(c))
                Clirepository.save(c);
        }catch(Exception e){
            Clirepository.save(c);
        }

        return "loggato come cliente: "+principal.getAttribute("name");
    }


    @GetMapping("/tassista")
    public RedirectView tassista(@AuthenticationPrincipal OAuth2User principal) {
        Tassista t = new Tassista(principal.getAttribute("email"),principal.getAttribute("given_name"),principal.getAttribute("family_name"));
        try{
            if(!Tasrepository.existsById(t.getEmail())) {
                Tasrepository.save(t);
                return new RedirectView("iscrizioneTassista");
            }else{
                if(Tasrepository.findAll().contains(t)){
                    return new RedirectView("iscrizioneTassista");
                }else{
                    return new RedirectView("tassistaHome");
                }

            }
        }catch(Exception e){
            Tasrepository.save(t);
            return new RedirectView("iscrizioneTassista");
        }
    }

    @GetMapping("/iscrizioneTassista")
    public ModelAndView iscrTas(@AuthenticationPrincipal OAuth2User principal){
        Tassista t = new Tassista(principal.getAttribute("email"),principal.getAttribute("given_name"),principal.getAttribute("family_name"));
        try{
            if(Tasrepository.findAll().contains(t)){
                ModelAndView mv = new ModelAndView();
                mv.setViewName("iscrizioneTassista.html");
                return mv;
            }else{
                ModelAndView mv = new ModelAndView();
                mv.setViewName("error404.html");
                return mv;
            }
        }catch(Exception e){
            ModelAndView mv = new ModelAndView();
            mv.setViewName("error404.html");
            return mv;
        }
    }

    @PostMapping("/salvaDatiPatente")
    public String datiPatente(@AuthenticationPrincipal OAuth2User principal, @RequestBody Tassista datitas){
       try{
           Tassista t = new Tassista(principal.getAttribute("email"),principal.getAttribute("given_name"),principal.getAttribute("family_name"));
           t.setnPatente(datitas.getnPatente());
           t.setScadenza(datitas.getScadenza());
           t.setTarga(datitas.getTarga());
           Tasrepository.save(t);
           return "OK, Dati aggiornati correttamente";
       }catch(Exception e){
           return "Qualcosa è andato storto :(";
       }


    }
}
