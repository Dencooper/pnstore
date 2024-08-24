package com.dencooper.pnstore.service;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.dencooper.pnstore.domain.Cart;
import com.dencooper.pnstore.domain.CartDetail;
import com.dencooper.pnstore.domain.Product;
import com.dencooper.pnstore.domain.User;
import com.dencooper.pnstore.domain.dto.ProductCriteriaDTO;
import com.dencooper.pnstore.repository.CartDetailRepository;
import com.dencooper.pnstore.repository.CartRepository;
import com.dencooper.pnstore.repository.ProductRepository;
import com.dencooper.pnstore.repository.UserRepository;
import com.dencooper.pnstore.service.specification.ProductSpecification;

import jakarta.servlet.http.HttpSession;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;

    public ProductService(ProductRepository productRepository,
            CartRepository cartRepository, CartDetailRepository cartDetailRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
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

    public Page<Product> fetchAllProducts(Pageable pageable) {
        return this.productRepository.findAll(pageable);
    }

    public void handleDeleteProductById(long id) {
        this.productRepository.deleteById(id);
    }

    public Cart fetchCartByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    public CartDetail fetchCartDetailById(long id) {
        Optional<CartDetail> cdOptional = this.cartDetailRepository.findById(id);
        if (cdOptional.isPresent()) {
            return cdOptional.get();
        }
        return null;

    }

    public void handleAddProductToCart(User user, Product product, HttpSession session, long quantity) {
        if (user != null) {
            Cart cart = this.cartRepository.findByUser(user);

            if (cart == null) {
                cart = new Cart();
                cart.setUser(user);
                cart.setSum(0);
                this.cartRepository.save(cart);
            }

            if (product != null) {
                CartDetail currentCartDetail = this.cartDetailRepository.findByCartAndProduct(cart, product);

                if (currentCartDetail == null) {
                    CartDetail newCartDetail = new CartDetail();
                    newCartDetail.setCart(cart);
                    newCartDetail.setProduct(product);
                    newCartDetail.setQuantity(quantity);
                    newCartDetail.setPrice(product.getPrice());
                    this.cartDetailRepository.save(newCartDetail);

                    int sum = cart.getSum() + 1;
                    cart.setSum(sum);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", sum);
                } else {
                    currentCartDetail.setQuantity(currentCartDetail.getQuantity() + quantity);
                    this.cartDetailRepository.save(currentCartDetail);
                }
            }
        }
    }

    public void handleDeleteCartDetail(CartDetail cartDetail, HttpSession session) {
        Cart cart = cartDetail.getCart();
        if (cart != null) {
            this.cartDetailRepository.delete(cartDetail);
            int sum = cart.getSum();
            if (sum == 1) {
                this.cartRepository.delete(cart);
                session.setAttribute("sum", 0);
            } else {
                cart.setSum(sum - 1);
                this.cartRepository.save(cart);
                session.setAttribute("sum", sum - 1);
            }

        }
    }

    public void handleUpdateCartBeforePayment(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cdOptional = this.cartDetailRepository.findById(cartDetail.getId());
            if (cdOptional.isPresent()) {
                CartDetail currentCartDetail = cdOptional.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(currentCartDetail);
            }
        }
    }

    public Specification<Product> buildPriceSpecification(List<String> price) {
        Specification<Product> combinedSpec = Specification.where(null);
        for (String p : price) {
            double min = 0;
            double max = 0;

            switch (p) {
                case "duoi-10-trieu":
                    min = 100000;
                    max = 10000000;
                    break;
                case "10-15-trieu":
                    min = 10000000;
                    max = 15000000;
                    break;
                case "15-20-trieu":
                    min = 15000000;
                    max = 20000000;
                    break;
                case "tren-20-trieu":
                    min = 20000000;
                    max = 200000000;
                    break;
            }

            if (min != 0 && max != 0) {
                Specification<Product> rangeSpec = ProductSpecification.matchMultiplePrice(min, max);
                combinedSpec = combinedSpec.or(rangeSpec);
            }
        }
        return combinedSpec;
    }

    public Page<Product> fetchProductsWithSpec(Pageable page, ProductCriteriaDTO productCriteriaDTO) {
        if (productCriteriaDTO.getPrice() == null
                && productCriteriaDTO.getFactory() == null
                && productCriteriaDTO.getTarget() == null) {
            return this.productRepository.findAll(page);
        }
        Specification<Product> combinedSpec = Specification.where(null);

        if (productCriteriaDTO.getTarget() != null && productCriteriaDTO.getTarget().isPresent()) {
            Specification<Product> currentSpec = ProductSpecification
                    .matchListTarget(productCriteriaDTO.getTarget().get());
            combinedSpec = combinedSpec.and(currentSpec);
        }

        if (productCriteriaDTO.getFactory() != null && productCriteriaDTO.getFactory().isPresent()) {
            Specification<Product> currentSpec = ProductSpecification
                    .matchListFactory(productCriteriaDTO.getFactory().get());
            combinedSpec = combinedSpec.and(currentSpec);
        }

        if (productCriteriaDTO.getPrice() != null && productCriteriaDTO.getPrice().isPresent()) {
            Specification<Product> currentSpec = this.buildPriceSpecification(productCriteriaDTO.getPrice().get());
            combinedSpec = combinedSpec.and(currentSpec);
        }

        return this.productRepository.findAll(combinedSpec, page);
    }

    public long countProduct() {
        return this.productRepository.count();
    }

    public long countProductByFatory(String factory) {
        return this.productRepository.countByFactory(factory);
    }

}
