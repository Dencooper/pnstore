package com.dencooper.pnstore.controller.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.dencooper.pnstore.domain.Product;
import com.dencooper.pnstore.domain.Product_;
import com.dencooper.pnstore.domain.dto.ProductCriteriaDTO;
import com.dencooper.pnstore.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LaptopController {
    private ProductService productService;

    public LaptopController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    public String getProductPage(Model model,
            ProductCriteriaDTO productCriteriaDTO,
            HttpServletRequest request) {

        int page = 1;
        try {
            if (productCriteriaDTO.getPage().isPresent()) {
                page = Integer.parseInt(productCriteriaDTO.getPage().get());
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        Pageable pageable = PageRequest.of(page - 1, 4);

        if (productCriteriaDTO.getSort() != null && productCriteriaDTO.getSort().isPresent()) {
            String sort = productCriteriaDTO.getSort().get();
            if (sort.equals("gia-giam-dan")) {
                pageable = PageRequest.of(page - 1, 4, Sort.by(Product_.PRICE).descending());
            } else if (sort.equals("gia-tang-dan")) {
                pageable = PageRequest.of(page - 1, 4, Sort.by(Product_.PRICE).ascending());
            }
        }

        Page<Product> pageProducts = this.productService.fetchProductsWithSpec(pageable, productCriteriaDTO);

        List<Product> listProducts = pageProducts.getContent().size() > 0 ? pageProducts.getContent()
                : new ArrayList<Product>();

        String qs = request.getQueryString();
        if (qs != null && !qs.isBlank()) {
            // remove page
            qs = qs.replace("page=" + page, "");
        }

        model.addAttribute("products", listProducts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageProducts.getTotalPages());
        model.addAttribute("queryString", qs);
        return "client/product/show";
    }

    @GetMapping("/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable("id") long id) {
        Product product = this.productService.fetchProductById(id);
        model.addAttribute("product", product);
        return "client/product/detail";
    }

}
