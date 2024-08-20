package com.dencooper.pnstore.controller.client;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        Pageable pageable = PageRequest.of(0, 4);
        Page<Product> pageProduct = this.productService.fetchAllProducts(pageable);
        List<Product> listProducts = pageProduct.getContent();
        model.addAttribute("products", listProducts);
        return "client/homepage/show";
    }

}
