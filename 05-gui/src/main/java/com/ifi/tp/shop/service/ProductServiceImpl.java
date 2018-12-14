package com.ifi.tp.shop.service;

import com.ifi.tp.shop.bo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private RestTemplate restTemplate;
    private String productServiceUrl;

    @Autowired
    public ProductServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Product> listProducts() {
        var url = productServiceUrl + "/shop";
        var products = restTemplate.getForObject(url, Product[].class);
        return Arrays.asList(products);
    }

    @Override
    public Product getProduct(int id) {
        var url = productServiceUrl + "/shop/{id}";
        return restTemplate.getForObject(url, Product.class, id);
    }

    @Value("${product.service.url}")
    void setPokemonServiceUrl(String productServiceUrl) {
        this.productServiceUrl = productServiceUrl;
    }
}
