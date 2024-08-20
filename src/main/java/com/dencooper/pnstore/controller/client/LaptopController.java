package com.dencooper.pnstore.controller.client;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.dencooper.pnstore.domain.Product;
import com.dencooper.pnstore.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LaptopController {
    private ProductService productService;

    public LaptopController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String getProductPage(Model model,
            @RequestParam("page") Optional<String> pageOptional) {

        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        Pageable pageable = PageRequest.of(page - 1, 4);
        Page<Product> pageProduct = this.productService.fetchAllProducts(pageable);
        List<Product> listProducts = pageProduct.getContent();

        model.addAttribute("products", listProducts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageProduct.getTotalPages());
        return "client/product/show";
    }

}
