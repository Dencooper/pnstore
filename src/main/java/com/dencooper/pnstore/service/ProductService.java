package com.dencooper.pnstore.service;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dencooper.pnstore.domain.Cart;
import com.dencooper.pnstore.domain.CartDetail;
import com.dencooper.pnstore.domain.Product;
import com.dencooper.pnstore.domain.User;
import com.dencooper.pnstore.repository.CartDetailRepository;
import com.dencooper.pnstore.repository.CartRepository;
import com.dencooper.pnstore.repository.ProductRepository;
import com.dencooper.pnstore.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class ProductService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;

    public ProductService(UserRepository userRepository, ProductRepository productRepository,
            CartRepository cartRepository, CartDetailRepository cartDetailRepository) {
        this.userRepository = userRepository;
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
}
