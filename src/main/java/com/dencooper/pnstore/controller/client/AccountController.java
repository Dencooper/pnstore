package com.dencooper.pnstore.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dencooper.pnstore.domain.User;
import com.dencooper.pnstore.service.UserService;
import com.dencooper.pnstore.utils.UploadFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {
    private final UserService userService;
    private final UploadFile uploadFile;

    public AccountController(UserService userService, UploadFile uploadFile) {
        this.userService = userService;
        this.uploadFile = uploadFile;
    }

    @GetMapping("/manage-account")
    public String getManageAccountPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");

        User currentUser = this.userService.fetchUserById(id);

        model.addAttribute("currentUser", currentUser);

        return "client/homepage/account";
    }

    @PostMapping("/manage-account/update")
    public String updateAccount(@ModelAttribute("currentUser") User currentUser) {
        User updatedUser = this.userService.fetchUserById(currentUser.getId());
        updatedUser.setFullName(currentUser.getFullName());
        updatedUser.setPhone(currentUser.getPhone());
        updatedUser.setAddress(currentUser.getAddress());
        this.userService.handleSaveUser(updatedUser);
        return "redirect:/manage-account";
    }

    @GetMapping("/manage-account/update/avatar")
    public String getUpdateAvatarPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        User currentUser = this.userService.fetchUserById(id);
        model.addAttribute("currentUser", currentUser);
        return "client/homepage/updateAvatar";
    }

    @PostMapping("/manage-account/update/avatar")
    public String updateAvatar(@ModelAttribute("currentUser") User currentUser, HttpServletRequest request,
            @RequestParam("avatarFile") MultipartFile file) {
        HttpSession session = request.getSession(false);
        User updatedUser = this.userService.fetchUserById(currentUser.getId());
        String avatar = this.uploadFile.handleSaveUploadFile(file, "avatar");
        updatedUser.setAvatar(avatar);
        this.userService.handleSaveUser(updatedUser);
        session.setAttribute("avatar", avatar);
        return "redirect:/manage-account";
    }

}
