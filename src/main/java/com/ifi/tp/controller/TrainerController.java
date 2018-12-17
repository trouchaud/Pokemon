package com.ifi.tp.controller;

import com.ifi.tp.trainers.bo.Trainer;
import com.ifi.tp.trainers.service.TrainerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class TrainerController {

    final String viewDirectory = "trainer/";

    @Autowired
    private TrainerService trainerService;

    @GetMapping("/arena/{name}")
    String getTrainers(HttpServletRequest request, ModelMap model, @PathVariable String name){
//        String user = (String)request.getSession().getAttribute("user");
//        if(!name.equals(user)){
//            model.addAttribute("message", "You are not authorised to do that");
//            return "default/erreur";
//        }
        model.addAttribute("trainer", trainerService.getTrainer(name));
        return viewDirectory.concat("arenaFight");
    }

    @GetMapping("/trainers")
    String getTrainers(ModelMap model){
        model.addAttribute("trainers", trainerService.getAllTrainers());
        return viewDirectory.concat("trainers");
    }

    @GetMapping("/trainers/{name}")
    String getTrainer(ModelMap model, @PathVariable String name){
        model.addAttribute("trainer", trainerService.getTrainer(name));
        return viewDirectory.concat("trainer");
    }

    @GetMapping("/team/{name}")
    String getTeam(ModelMap model, @PathVariable String name){
        Trainer trainer = trainerService.getTrainer(name);
        model.addAttribute("teams", trainer.getTeam());
        return viewDirectory.concat("team");
    }

    @GetMapping("/trainers/{name}/arena")
    String getOtherTrainer(ModelMap model, @PathVariable String name){
        List<Trainer> oldTrainers = trainerService.getAllTrainers();
        List<Trainer> trainers = new ArrayList<Trainer>();

        for (Iterator<Trainer> iter = oldTrainers.listIterator(); iter.hasNext(); ) {
            Trainer a = iter.next();
            if (!a.getName().equals(name)) {
                trainers.add(a);
            }
        }

        model.addAttribute("trainers", trainers);
        return viewDirectory.concat("fightTrainer");
    }

    @GetMapping("/trainers/{name}/inventory")
    String getInventory(ModelMap model, @PathVariable String name){

        Trainer trainer = trainerService.getTrainer(name);
        model.addAttribute("products", trainer.getInventory());
        model.addAttribute("trainer",trainer);

        return "product/inventory";
    }

}
