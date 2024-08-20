package com.dencooper.pnstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dencooper.pnstore.domain.Cart;
import com.dencooper.pnstore.domain.CartDetail;
import com.dencooper.pnstore.domain.Order;
import com.dencooper.pnstore.domain.OrderDetail;
import com.dencooper.pnstore.domain.User;
import com.dencooper.pnstore.repository.CartDetailRepository;
import com.dencooper.pnstore.repository.CartRepository;
import com.dencooper.pnstore.repository.OrderDetailRepository;
import com.dencooper.pnstore.repository.OrderRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository,
            CartRepository cartRepository, CartDetailRepository cartDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
    }

    public void handlePlaceOrder(User currentUser, HttpSession session, String receiverName, String receiverAddress,
            String receiverPhone) {
        Cart cart = currentUser.getCart();
        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetails();
            if (cartDetails != null) {
                Order order = new Order();
                order.setUser(currentUser);
                order.setReceiverName(receiverName);
                order.setReceiverAddress(receiverAddress);
                order.setReceiverPhone(receiverPhone);
                order.setStatus("PENDING");

                double totalPrice = 0;
                for (CartDetail cd : cartDetails) {
                    totalPrice += cd.getPrice() * cd.getQuantity();
                }

                order.setTotalPrice(totalPrice);
                order = this.orderRepository.save(order);

                for (CartDetail cd : cartDetails) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cd.getProduct());
                    orderDetail.setPrice(cd.getPrice());
                    orderDetail.setQuantity(cd.getQuantity());
                    this.orderDetailRepository.save(orderDetail);
                }

                for (CartDetail cd : cartDetails) {
                    this.cartDetailRepository.deleteById(cd.getId());
                }

                this.cartRepository.deleteById(cart.getId());

                session.setAttribute("sum", 0);
            }

        }
    }
}
