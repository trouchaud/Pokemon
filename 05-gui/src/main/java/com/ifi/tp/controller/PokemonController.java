package com.ifi.tp.controller;

import com.ifi.tp.pokemonTypes.service.PokemonService;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PokemonController {

    private PokemonService pokemonService;

    @Autowired
    PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/pokemons")
    public String pokemons(ModelMap model, @RequestParam(defaultValue = "back") String view){
        model.addAttribute("pokemons", pokemonService.listPokemonsTypes());

        if("front".equals(view)){
            model.addAttribute("view_front", true);
        }
        else{
            model.addAttribute("view_back", true);
        }

        return "pokemons";
    }

}
