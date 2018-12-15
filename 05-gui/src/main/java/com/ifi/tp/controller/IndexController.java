package com.ifi.tp.controller;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.web.servlet.JtwigRenderer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @GetMapping("/")
    String index(ModelMap model){
        model.addAttribute("var", "world");
        return "index";
    }

    @PostMapping(value = "/registerTrainer")
    String registerNewTrainer(HttpServletRequest request, ModelMap model, String trainerName){
        request.getSession().setAttribute("user", trainerName);
        model.addAttribute("name", trainerName);
        return "register";
    }

}
