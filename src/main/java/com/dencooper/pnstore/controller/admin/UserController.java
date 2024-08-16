package com.dencooper.pnstore.controller.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import com.dencooper.pnstore.domain.Role;
import com.dencooper.pnstore.domain.User;
import com.dencooper.pnstore.service.UserService;
import com.dencooper.pnstore.utils.UploadFile;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UploadFile uploadFile;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, UploadFile uploadFile) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.uploadFile = uploadFile;
    }

    @GetMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.fetchAllUsers();
        model.addAttribute("users", users);
        return "admin/user/show";
    }

    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String createUser(Model model,
            @ModelAttribute("newUser") @Valid User newUser,
            BindingResult newUserBindingResult,
            @RequestParam("avatarFile") MultipartFile avatarFile) {

        if (newUserBindingResult.hasErrors()) {
            return "admin/user/create";
        }

        String hashPassword = this.passwordEncoder.encode(newUser.getPassword());
        String avatar = this.uploadFile.handleSaveUploadFile(avatarFile, "avatar");
        newUser.setPassword(hashPassword);
        newUser.setAvatar(avatar);
        String role_name = newUser.getRole().getName();
        Role role = this.userService.getRoleByName(role_name);
        newUser.setRole(role);
        this.userService.handleSaveUser(newUser);
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/{id}")
    public String getViewUserPage(Model model, @PathVariable("id") long id) {
        User user = this.userService.fetchUserById(id);
        model.addAttribute("user", user);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable("id") long id) {
        model.addAttribute("currentUser", this.userService.fetchUserById(id));
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String updateUser(@ModelAttribute("currentUser") User currentUser) {
        User updatedUser = this.userService.fetchUserById(currentUser.getId());
        if (updatedUser != null) {
            updatedUser.setFullName(currentUser.getFullName());
            updatedUser.setAddress(currentUser.getAddress());
            updatedUser.setPhone(currentUser.getPhone());
            this.userService.handleSaveUser(updatedUser);
            return "redirect:/admin/user";
        }
        return null;
    }

    @GetMapping("/admin/user/update/avatar/{id}")
    public String getUpdateAvatarPage(Model model, @PathVariable("id") long id) {
        model.addAttribute("currentUser", this.userService.fetchUserById(id));
        return "admin/user/updateAvatar";
    }

    @PostMapping("/admin/user/update/avatar")
    public String updateAvatar(Model model, @ModelAttribute("currentUser") User currentUser,
            @RequestParam("avatarFile") MultipartFile file) {
        User updatedUser = this.userService.fetchUserById(currentUser.getId());
        String avatar = this.uploadFile.handleSaveUploadFile(file, "avatar");
        String id = Long.toString(currentUser.getId());
        updatedUser.setAvatar(avatar);
        this.userService.handleSaveUser(updatedUser);
        return "redirect:/admin/user/update/" + id;
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable("id") long id) {
        model.addAttribute("currentUser", this.userService.fetchUserById(id));
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String getDeleteUserPage(@ModelAttribute("currentUser") User currentUser) {
        this.userService.handleDeleteUserById(currentUser.getId());
        return "redirect:/admin/user";
    }

}
