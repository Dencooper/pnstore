package com.dencooper.pnstore.controller.client;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dencooper.pnstore.domain.Product;
import com.dencooper.pnstore.service.ProductService;

@Controller
public class HomePageController {
    private ProductService productService;

    public HomePageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        List<Product> products = this.productService.fetchAllProducts();
        model.addAttribute("products", products);
        return "client/homepage/show";
    }

}
