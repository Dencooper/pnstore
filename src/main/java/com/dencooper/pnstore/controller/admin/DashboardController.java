package com.dencooper.pnstore.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dencooper.pnstore.service.OrderService;
import com.dencooper.pnstore.service.ProductService;
import com.dencooper.pnstore.service.UserService;

@Controller
public class DashboardController {
    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;

    public DashboardController(UserService userService, ProductService productService, OrderService orderService) {
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("/admin")
    public String getDashboard(Model model) {
        model.addAttribute("countUser", this.userService.countUser());
        model.addAttribute("countProduct", this.productService.countProduct());
        model.addAttribute("countOrder", this.orderService.countOrder());
        return "admin/dashboard/show";
    }

}
