package com.ifi.tp.controller;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.web.servlet.JtwigRenderer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @GetMapping("/")
    String index(ModelMap model){
        model.addAttribute("var", "world");
        return "index";
    }

    @PostMapping(value = "/registerTrainer")
    String registerNewTrainer(ModelMap model, String trainerName){
        model.addAttribute("name", trainerName);
        return "register";
    }

}
