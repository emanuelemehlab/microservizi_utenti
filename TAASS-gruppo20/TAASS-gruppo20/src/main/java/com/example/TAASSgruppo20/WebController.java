package com.example.TAASSgruppo20;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class WebController {

    @GetMapping("/")
    public ModelAndView login(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login.html");
        return mv;
    }
}
