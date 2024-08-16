package com.dencooper.pnstore.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dencooper.pnstore.domain.User;

@Controller
public class DashboardController {
    @GetMapping("/admin")
    public String getDashboard() {
        return "admin/dashboard/show";
    }

}
