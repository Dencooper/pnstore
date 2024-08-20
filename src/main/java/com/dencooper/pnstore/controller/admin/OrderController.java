package com.dencooper.pnstore.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.dencooper.pnstore.domain.Order;
import com.dencooper.pnstore.service.OrderService;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin/order")
    public String getOrder(Model model,
            @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e) {
        }
        Pageable pageable = PageRequest.of(page - 1, 1);
        Page<Order> pageOrders = this.orderService.fetchAllOrders(pageable);
        List<Order> listOrders = pageOrders.getContent();

        model.addAttribute("orders", listOrders);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageOrders.getTotalPages());

        return "admin/order/show";
    }

    @GetMapping("/admin/order/{id}")
    public String getOrder(Model model, @PathVariable long id) {
        Order order = this.orderService.fetchOrderById(id);
        model.addAttribute("order", order);
        return "admin/order/detail";
    }

    @GetMapping("/admin/order/update/{id}")
    public String getUpdateOrderPage(Model model, @PathVariable long id) {
        Order currentOrder = this.orderService.fetchOrderById(id);
        model.addAttribute("currentOrder", currentOrder);
        return "admin/order/update";
    }

    @PostMapping("/admin/order/update")
    public String updateOrder(@ModelAttribute("currentOrder") Order currentOrder) {
        Order updateOrder = this.orderService.fetchOrderById(currentOrder.getId());
        if (updateOrder != null) {
            updateOrder.setStatus(currentOrder.getStatus());
        }
        this.orderService.handleSaveOrder(updateOrder);
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/delete/{id}")
    public String getDeleteOrderPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        return "admin/order/delete";
    }

    @PostMapping("/admin/order/delete")
    public String deleteOrder(@RequestParam("id") long id) {
        Order order = this.orderService.fetchOrderById(id);
        this.orderService.handelDeleteOrder(order);
        return "redirect:/admin/order";
    }

}
