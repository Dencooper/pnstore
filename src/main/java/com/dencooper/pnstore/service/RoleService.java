package com.dencooper.pnstore.service;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dencooper.pnstore.domain.Role;
import com.dencooper.pnstore.repository.RoleRepository;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role handleCreateRole(Role role) {
        return this.roleRepository.save(role);
    }

    public Role handleFetchRoleById(long id) {
        Optional<Role> roleOptional = this.roleRepository.findById(id);
        if (roleOptional.isPresent()) {
            return roleOptional.get();
        }
        return null;
    }

    public List<Role> handleFetchAllRoles() {
        return this.roleRepository.findAll();
    }

    public Role handleUpdateRole(Role role) {
        Optional<Role> roleOptional = this.roleRepository.findById(role.getId());
        if (roleOptional.isPresent()) {
            Role updatedRole = roleOptional.get();
            updatedRole.setName(role.getName());
            updatedRole.setDescription(role.getDescription());
        }
        return null;
    }

    public void handleDeleteRoleById(long id) {
        this.roleRepository.deleteById(id);
    }

    public Role fetchRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }
}
