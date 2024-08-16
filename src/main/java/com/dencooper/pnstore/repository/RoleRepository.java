package com.dencooper.pnstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dencooper.pnstore.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
