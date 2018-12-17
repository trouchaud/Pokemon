package com.ifi.tp.controller;

import com.ifi.tp.pokemonTypes.bo.PokemonType;
import com.ifi.tp.pokemonTypes.service.PokemonService;
import com.ifi.tp.shop.bo.Product;
import com.ifi.tp.shop.service.ProductService;
import com.ifi.tp.trainers.bo.Pokemon;
import com.ifi.tp.trainers.bo.Trainer;
import com.ifi.tp.trainers.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class ProductController {

    final String viewDirectory = "product/";

    @Autowired
    private ProductService productService;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private PokemonService pokemonService;

    @GetMapping("/shop")
    public String products(ModelMap model, @RequestParam(defaultValue = "back") String view){
        model.addAttribute("products", productService.listProducts());

        return viewDirectory.concat("shop");
    }

    @GetMapping("/shop/{name}/product/{productId}")
    public String setProductToInventory(ModelMap model,
                                        @RequestParam(defaultValue = "back") String view,
                                        @PathVariable String name,
                                        @PathVariable int productId){
        model.addAttribute("product", productService.getProduct(productId));
        model.addAttribute("trainer", trainerService.getTrainer(name));

        return viewDirectory.concat("buy");
    }

    @PostMapping(value = "/shop/{name}/product/{productId}")
    String buyProduct(HttpServletRequest request,
                              ModelMap model,
                              @PathVariable String name,
                              @PathVariable int productId,
                              @RequestParam("quantity") int quantity){
        String user = (String)request.getSession().getAttribute("user");
        if(!name.equals(user)){
            model.addAttribute("message", "You are not authorised to do that");
            return "default/erreur";
        }

        Trainer trainer = trainerService.getTrainer(name);
        Product product = productService.getProduct(productId);
        int money = trainer.getMoney() - product.getPrice() * quantity;

        List<com.ifi.tp.trainers.bo.Product> inventory = trainer.getInventory();

        Boolean exist = false;

        for (Iterator<com.ifi.tp.trainers.bo.Product> iter = inventory.listIterator(); iter.hasNext(); ) {
            com.ifi.tp.trainers.bo.Product p = iter.next();
            if (p.getProductId() == product.getId()) {
                exist = true;
                p.setQuantity(p.getQuantity()+quantity);
            }
        }

        if(!exist){
            com.ifi.tp.trainers.bo.Product p2 = new com.ifi.tp.trainers.bo.Product();
            p2.setQuantity(quantity);
            p2.setProductId(productId);
            p2.setDetail(product);
            inventory.add(p2);
        }

        trainer.setMoney(money);

        trainerService.putTrainer(trainer);

        return "redirect:/trainers/"+name;
    }

    @GetMapping("/shop/{name}/product/{productId}/use")
    public String getPokemonForProduct(ModelMap model,
                                        @RequestParam(defaultValue = "back") String view,
                                        @PathVariable String name,
                                        @PathVariable int productId){

        Trainer trainer = trainerService.getTrainer(name);
        List<Pokemon> nPokemon = new ArrayList<Pokemon>();

        // TODO action capture

        for (Iterator<Pokemon> iter = trainer.getTeam().listIterator(); iter.hasNext(); ) {
            Pokemon p = iter.next();
            PokemonType initialPokemon = pokemonService.getPokemonType(p.getPokemonNumber());
            if (p.getHp() < initialPokemon.getStats().getHp() + p.getLevel()) {
                nPokemon.add(p);
            }
        }

        model.addAttribute("product", productService.getProduct(productId));
        model.addAttribute("teams", nPokemon);

        return viewDirectory.concat("useConsume");
    }

    @GetMapping("/shop/{name}/product/{productId}/use/{pokemonId}")
    public String setConsumeOnPokemon(ModelMap model,
                                      @RequestParam(defaultValue = "back") String view,
                                      @PathVariable String name,
                                      @PathVariable int productId,
                                      @PathVariable int pokemonId){

        Trainer trainer = trainerService.getTrainer(name);
        com.ifi.tp.trainers.bo.Product searchProduct = new com.ifi.tp.trainers.bo.Product();

        for (Iterator<com.ifi.tp.trainers.bo.Product> iter = trainer.getInventory().listIterator(); iter.hasNext(); ) {
            com.ifi.tp.trainers.bo.Product p = iter.next();
            if (productId == p.getProductId()) {
                searchProduct = p;
            }
        }

        for (Iterator<Pokemon> iter = trainer.getTeam().listIterator(); iter.hasNext(); ) {
            Pokemon p = iter.next();
            if (pokemonId == p.getId()) {
                searchProduct.use(p, pokemonService.getPokemonType(p.getPokemonNumber()));
            }
        }

        trainerService.putTrainer(trainer);

        return "redirect:/trainers/"+name;
    }

}
