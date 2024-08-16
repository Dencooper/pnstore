package com.dencooper.pnstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dencooper.pnstore.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
