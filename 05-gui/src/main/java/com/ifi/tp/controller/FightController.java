package com.ifi.tp.controller;

import com.ifi.tp.fight.bo.Fight;
import com.ifi.tp.fight.service.FightService;
import com.ifi.tp.pokemonTypes.service.PokemonService;
import com.ifi.tp.trainers.service.TrainerService;
import org.jtwig.web.servlet.JtwigRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class FightController {

    final String viewDirectory = "fight/";

    private final JtwigRenderer renderer = JtwigRenderer.defaultRenderer();

    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private FightService fightService;


    @GetMapping("/fight/{name}")
    String fightHistory(ModelMap model, @PathVariable String name){
        List<Fight> fights = fightService.getAllFightsForFighter(name);
//        List<Fight> fights = fightService.getAllFights();
        model.addAttribute("fights", fights);
        return viewDirectory+"history";
    }
}
