package com.dencooper.pnstore.controller.admin;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dencooper.pnstore.domain.Role;
import com.dencooper.pnstore.domain.User;
import com.dencooper.pnstore.service.RoleService;
import com.dencooper.pnstore.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String createUser(Model model,
            @ModelAttribute("newUser") @Valid User newUser,
            BindingResult newUserBindingResult) {

        if (newUserBindingResult.hasErrors()) {
            return "admin/user/create";
        }

        String hashPassword = this.passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(hashPassword);
        String role_name = newUser.getRole().getName();
        Role role = this.roleService.fetchRoleByName(role_name);
        newUser.setRole(role);
        this.userService.handleCreateUser(newUser);
        return "admin/user/show";
    }

    @PostMapping("/admin/user/update")
    public String updateUser(Model model,
            @ModelAttribute("updatedUser") @Valid User updatedUser,
            BindingResult newUserBindingResult) {

        if (newUserBindingResult.hasErrors()) {
            return "admin/user/update";
        }
        this.userService.handleUpdateUser(updatedUser);
        return "admin/user/show";
    }

}
