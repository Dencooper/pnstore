package com.dencooper.pnstore.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dencooper.pnstore.domain.User;
import com.dencooper.pnstore.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {
    private UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/manage-account")
    public String getManageAccountPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");

        User currentUser = this.userService.fetchUserById(id);

        model.addAttribute("currentUser", currentUser);

        return "client/auth/account";
    }
}
