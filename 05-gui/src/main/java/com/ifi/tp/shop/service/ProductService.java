package com.ifi.tp.shop.service;

import com.ifi.tp.shop.bo.Product;

import java.util.List;

public interface ProductService {

    List<Product> listProducts();
    Product getProduct(int id);

}
