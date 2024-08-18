package com.dencooper.pnstore.controller.client;

import java.net.http.HttpRequest;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dencooper.pnstore.domain.Cart;
import com.dencooper.pnstore.domain.CartDetail;
import com.dencooper.pnstore.domain.Product;
import com.dencooper.pnstore.domain.User;
import com.dencooper.pnstore.service.ProductService;
import com.dencooper.pnstore.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ShoppingController {
    private final ProductService productService;
    private final UserService userService;

    public ShoppingController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable("id") long productId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = this.userService.fetchUserById((long) session.getAttribute("id"));
        Product product = this.productService.fetchProductById(productId);
        this.productService.handleAddProductToCart(user, product, session, 1);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = this.userService.fetchUserById((long) session.getAttribute("id"));
        Cart cart = this.productService.fetchCartByUser(user);
        List<CartDetail> cartDetails = cart.getCartDetails();
        double totalPrice = 0;
        for (CartDetail cartDetail : cartDetails) {
            totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        return "client/cart/show";
    }

}
