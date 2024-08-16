package com.dencooper.pnstore.service;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dencooper.pnstore.domain.Product;
import com.dencooper.pnstore.repository.ProductRepository;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product handleSaveProduct(Product product) {
        return this.productRepository.save(product);
    }

    public Product fetchProductById(long id) {
        Optional<Product> productOptional = this.productRepository.findById(id);
        if (productOptional.isPresent()) {
            return productOptional.get();
        }
        return null;
    }

    public List<Product> fetchAllProducts() {
        return this.productRepository.findAll();
    }

    public Product handleUpdateProduct(Product product) {
        Optional<Product> productOptional = this.productRepository.findById(product.getId());
        if (productOptional.isPresent()) {
            Product updatedProduct = productOptional.get();
            updatedProduct.setName(product.getName());
            updatedProduct.setPrice(product.getPrice());
            updatedProduct.setDetailDesc(product.getDetailDesc());
            updatedProduct.setShortDesc(product.getShortDesc());
            updatedProduct.setFactory(product.getFactory());
            updatedProduct.setTarget(product.getTarget());
            updatedProduct.setQuantity(product.getQuantity());
            updatedProduct.setSold(product.getSold());
        }
        return null;
    }

    public void handleDeleteProductById(long id) {
        this.productRepository.deleteById(id);
    }
}
