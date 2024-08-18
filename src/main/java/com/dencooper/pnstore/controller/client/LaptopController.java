package com.dencooper.pnstore.controller.client;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.dencooper.pnstore.domain.Product;
import com.dencooper.pnstore.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LaptopController {
    private ProductService productService;

    public LaptopController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String getProductPage(Model model) {
        List<Product> products = this.productService.fetchAllProducts();

        model.addAttribute("products", products);
        return "client/product/show";
    }

}
