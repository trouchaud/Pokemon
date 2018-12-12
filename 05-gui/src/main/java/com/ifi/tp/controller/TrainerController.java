package com.ifi.tp.controller;

import com.ifi.tp.trainers.service.TrainerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TrainerController {

    @Autowired
    private TrainerService trainerService;

    @GetMapping("/trainers")
    String index(ModelMap model){
        model.addAttribute("trainers", trainerService.getAllTrainers());
        return "trainers";
    }

    @GetMapping("/trainers/{name}")
    String index(ModelMap model, @PathVariable String name){
        model.addAttribute("trainer", trainerService.getTrainer(name));

        return "trainer";
    }

}
