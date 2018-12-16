package com.ifi.tp.controller;

import com.ifi.tp.fight.bo.Fight;
import com.ifi.tp.fight.service.FightService;
import com.ifi.tp.pokemonTypes.service.PokemonService;
import com.ifi.tp.trainers.bo.Trainer;
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
        model.addAttribute("fights", fights);
        return viewDirectory+"history";
    }

    @GetMapping("/fight/{name1}/{name2}")
    String fightBetweenFighterHistory(ModelMap model,
                                      @PathVariable String name1,
                                      @PathVariable String name2){
        List<Fight> fights = fightService.getAllFightsBetweenFighter(name1, name2);
        model.addAttribute("fights", fights);
        model.addAttribute("name2", name2);
        return viewDirectory+"history";
    }

    @GetMapping("/fight/engage/{name1}/{name2}")
    String setFight(ModelMap model,
                    @PathVariable String name1,
                    @PathVariable String name2){
        Trainer trainer1 = trainerService.getTrainer(name1);
        Trainer trainer2 = trainerService.getTrainer(name2);

        int i=0;
        boolean t1 = false;
        while(i<trainer1.getTeam().size()){
            if(trainer1.getTeam().get(i).getHp()>0){
                t1 = true;
                break;
            }
            i++;
        }

        i=0;
        boolean t2 = false;
        while(i<trainer2.getTeam().size()){
            if(trainer2.getTeam().get(i).getHp()>0){
                t2=true;
                break;
            }
            i++;
        }

        if(!t1||!t2){
            if(!t1)
                model.addAttribute("message",
                        "You are not able to fight, all your pokemons are KO.");
            else
                model.addAttribute("message",
                        "You are not able to fight this trainer, all his pokemons are KO.");
            return "default/erreur";
        }

        Fight fight = fightService.setFight(name1, name2);
        model.addAttribute("fights", List.of(fight));
        model.addAttribute("name2", name2);
        return viewDirectory+"history";
    }
}
