package com.ifi.tp.trainers.bo;

import com.ifi.tp.pokemonTypes.bo.PokemonType;

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
        if(this.detail.getName().equals("potion")||this.detail.getName().equals("super potion")){
            int adding = 0;
            if(this.detail.getName().equals("potion")) adding = 20;
            else adding = 50;

            int hp = pokemon.getHp();
            pokemon.setHp(hp + adding);
            if(hp + adding > initialPokemon.getStats().getHp() + pokemon.getLevel()){
                pokemon.setHp(initialPokemon.getStats().getHp() + pokemon.getLevel());
            }
        }
        this.setQuantity(this.getQuantity() - 1);
    }
}
