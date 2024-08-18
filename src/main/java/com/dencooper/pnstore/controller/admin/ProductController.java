package com.dencooper.pnstore.controller.admin;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.dencooper.pnstore.domain.Product;
import com.dencooper.pnstore.service.ProductService;
import com.dencooper.pnstore.utils.UploadFile;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProductController {
    private final ProductService productService;
    private final UploadFile uploadFile;

    public ProductController(ProductService productService, UploadFile uploadFile) {
        this.productService = productService;
        this.uploadFile = uploadFile;
    }

    @GetMapping("/admin/product")
    public String getProductPage(Model model) {
        List<Product> products = this.productService.fetchAllProducts();
        model.addAttribute("products", products);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String createProduct(@ModelAttribute("newProduct") @Valid Product newProduct,
            BindingResult newProductBindingResult,
            @RequestParam("imageFiles") MultipartFile[] imageFiles) {

        if (newProductBindingResult.hasErrors()) {
            return "admin/product/create";
        }
        ArrayList<String> listImages = new ArrayList<>();
        for (MultipartFile imageFile : imageFiles) {
            String image = this.uploadFile.handleSaveUploadFile(imageFile, "product");
            listImages.add(image);
        }
        newProduct.setImages(listImages);
        this.productService.handleSaveProduct(newProduct);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{id}")
    public String getViewProductPage(Model model, @PathVariable("id") long id) {
        Product product = this.productService.fetchProductById(id);
        model.addAttribute("product", product);
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable("id") long id) {
        Product currentProduct = this.productService.fetchProductById(id);
        model.addAttribute("currentProduct", currentProduct);
        model.addAttribute("totalImages", currentProduct.getImages().size());
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String updateProduct(@ModelAttribute("currentProduct") Product currentProduct) {
        Product updatedProduct = this.productService.fetchProductById(currentProduct.getId());
        if (updatedProduct != null) {
            updatedProduct.setName(currentProduct.getName());
            updatedProduct.setPrice(currentProduct.getPrice());
            updatedProduct.setDetailDesc(currentProduct.getDetailDesc());
            updatedProduct.setShortDesc(currentProduct.getShortDesc());
            updatedProduct.setQuantity(currentProduct.getQuantity());
            this.productService.handleSaveProduct(updatedProduct);
            return "redirect:/admin/product";
        }
        return null;

    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable("id") long id) {
        model.addAttribute("id", id);
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String deleteProduct(@RequestParam("id") long id) {
        this.productService.handleDeleteProductById(id);
        return "redirect:/admin/product";
    }

}