package com.ifi.tp.trainers.bo;

import com.ifi.tp.pokemonTypes.bo.PokemonType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Product {
    private int id;

    private int productId;

    private int quantity;

    private com.ifi.tp.shop.bo.Product detail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public com.ifi.tp.shop.bo.Product getDetail() {
        return detail;
    }

    public void setDetail(com.ifi.tp.shop.bo.Product detail) {
        this.detail = detail;
    }

    public void use(Pokemon pokemon, PokemonType initialPokemon){
        if(this.getDetail().getName().equals("potion")||this.getDetail().getName().equals("super potion")){
            int adding = 0;
            if(this.getDetail().getName().equals("potion")) adding = 20;
            else adding = 50;

            int hp = pokemon.getHp();
            pokemon.setHp(hp + adding);
            if(hp + adding > initialPokemon.getStats().getHp() + pokemon.getLevel()){
                pokemon.setHp(initialPokemon.getStats().getHp() + pokemon.getLevel());
            }
        }
        this.setQuantity(this.getQuantity() - 1);
    }

    public void use(Trainer trainer, PokemonType pokemon){
        if(!this.getDetail().getName().equals("pokeball")){
            return;
        }
        List<Pokemon> team = (ArrayList<Pokemon>) trainer.getTeam();
        int level = 1;

        if(team.size() > 0){
            level = team.get(0).getLevel();
        }

        if(team.size()>=6){
            return;
        }

        Pokemon newPokemon = new Pokemon();
        newPokemon.setType(pokemon);
        newPokemon.setPokemonNumber(pokemon.getId());
        int hp = pokemon.getStats().getHp();

        if(level>1){
            hp += level;
        }

        newPokemon.setHp(hp);
        newPokemon.setLevel(level);
        team.add(newPokemon);
        trainer.setTeam(team);

        this.setQuantity(this.getQuantity() - 1);
    }

    public void use(Trainer trainer){
        if(!this.getDetail().getName().equals("hyper recall")){
            return;
        }

        for (Iterator<Pokemon> iter = trainer.getTeam().listIterator(); iter.hasNext(); ) {
            Pokemon pok = iter.next();
            pok.setHp(pok.getType().getStats().getHp()+pok.getLevel());
        }

        this.setQuantity(this.getQuantity()-1);
    }
}
