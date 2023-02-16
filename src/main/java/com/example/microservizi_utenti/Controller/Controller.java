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

@CrossOrigin
@RestController
public class Controller {

    @Autowired
    private ClienteRepository Clirepository;

    @Autowired
    private TassistaRepository Tasrepository;

    @GetMapping("/")
    public String helloWorld(){
        return "Hello World";
    }
    @GetMapping("/loginCliente")
    public RedirectView cliente(@AuthenticationPrincipal OAuth2User principal) {
        Cliente c = new Cliente(principal.getAttribute("email"),principal.getAttribute("given_name"),principal.getAttribute("family_name"));
        try{
            if(!Clirepository.findAll().contains(c))
                Clirepository.save(c);
        }catch(Exception e){
            Clirepository.save(c);
        }

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/homeCliente");
        return redirectView;
//        return "loggato come cliente: "+principal.getAttribute("name");
    }

    @GetMapping("/getMailLogged")
    public String getMailLogged(@AuthenticationPrincipal OAuth2User principal) {

        return principal.getAttribute("email");
    }


    @GetMapping("/loginTassista")
    public String tassista(@AuthenticationPrincipal OAuth2User principal) {
        Tassista t = new Tassista(principal.getAttribute("email"),principal.getAttribute("given_name"),principal.getAttribute("family_name"));
        try{
            if(!Tasrepository.existsById(t.getEmail())) {
                Tasrepository.save(t);
//                return new RedirectView("iscrizioneTassista");
                return "Primo passo iscrizione tassista effettuato.";
            }else{
                if(Tasrepository.findAll().contains(t)){
//                    return new RedirectView("iscrizioneTassista");
                    return "Primo passo iscrizione tassista già effettuato.";
                }else{
//                    return new RedirectView("tassistaHome");
                    return "Login effettuato";
                }

            }
        }catch(Exception e){
            Tasrepository.save(t);
//            return new RedirectView("iscrizioneTassista");
            return e.getMessage();
        }
    }

    @GetMapping("/oauth2/authorization/google?{continue}")
    public RedirectView redirectlogin(@PathVariable("continue") String cont,@CookieValue(name = "Path") String path) {
        try{
            if(path.equals("loginCliente")){
                return new RedirectView("/loginCliente");
            } else if (path.equals("loginTassista")) {
                return new RedirectView("/loginTassista");
            }else{
                return new RedirectView(path);
            }
        }catch(Exception e){
            return new RedirectView(path);
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




}
