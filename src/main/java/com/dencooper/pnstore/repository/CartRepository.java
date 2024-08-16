package com.dencooper.pnstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dencooper.pnstore.domain.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
