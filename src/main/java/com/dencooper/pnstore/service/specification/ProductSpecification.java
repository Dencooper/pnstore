package com.dencooper.pnstore.service.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.dencooper.pnstore.domain.Product;
import com.dencooper.pnstore.domain.Product_;

public class ProductSpecification {

    public static Specification<Product> matchListFactory(List<String> factory) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(Product_.FACTORY)).value(factory);
    }

    public static Specification<Product> matchListTarget(List<String> target) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(Product_.TARGET)).value(target);
    }

    public static Specification<Product> matchMultiplePrice(double min, double max) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(
                root.get(Product_.PRICE), min, max);
    }

}
