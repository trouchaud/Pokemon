package com.ifi.tp.controller;

import com.ifi.tp.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {

    private ProductService productService;

    @Autowired
    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/shop")
    public String products(ModelMap model, @RequestParam(defaultValue = "back") String view){
        model.addAttribute("products", productService.listProducts());

        return "products/shop";
    }

}
