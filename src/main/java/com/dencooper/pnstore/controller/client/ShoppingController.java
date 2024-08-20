package com.dencooper.pnstore.controller.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dencooper.pnstore.domain.Cart;
import com.dencooper.pnstore.domain.CartDetail;
import com.dencooper.pnstore.domain.Order;
import com.dencooper.pnstore.domain.Product;
import com.dencooper.pnstore.domain.User;
import com.dencooper.pnstore.service.OrderService;
import com.dencooper.pnstore.service.ProductService;
import com.dencooper.pnstore.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ShoppingController {
    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;

    public ShoppingController(ProductService productService, UserService userService, OrderService orderService) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
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
        List<CartDetail> cartDetails = user.getCart() == null ? new ArrayList<CartDetail>()
                : user.getCart().getCartDetails();

        double totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);

        model.addAttribute("cart", user.getCart());

        return "client/shopping/cart";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String deleteDetailCartById(@PathVariable("id") long cartDetalId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        CartDetail cartDetail = this.productService.fetchCartDetailById(cartDetalId);
        if (cartDetail != null) {
            this.productService.handleDeleteCartDetail(cartDetail, session);
        }
        return "redirect:/";
    }

    @PostMapping("/confirm-payment")
    public String getCheckOutPage(@ModelAttribute("cart") Cart cart) {
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        this.productService.handleUpdateCartBeforePayment(cartDetails);
        return "redirect:/payment";
    }

    @GetMapping("/payment")
    public String getCheckOutPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User currentUser = new User();
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = this.productService.fetchCartByUser(currentUser);

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);

        return "client/shopping/payment";
    }

    @PostMapping("/place-order")
    public String handlePlaceOrder(
            HttpServletRequest request,
            @RequestParam("receiverName") String receiverName,
            @RequestParam("receiverAddress") String receiverAddress,
            @RequestParam("receiverPhone") String receiverPhone) {

        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        User currentUser = this.userService.fetchUserById(id);
        this.orderService.handlePlaceOrder(currentUser, session, receiverName, receiverAddress, receiverPhone);
        return "redirect:/thanks";
    }

    @GetMapping("/thanks")
    public String getThankYouPage() {
        return "client/shopping/thanks";
    }

    @GetMapping("/order-history")
    public String getOrderHistoryPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        User currentUser = this.userService.fetchUserById(id);
        List<Order> orders = this.orderService.fetchAllOrdersByUser(currentUser);
        model.addAttribute("orders", orders);
        return "client/shopping/orders";
    }

}
