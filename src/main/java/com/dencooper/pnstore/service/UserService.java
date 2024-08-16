package com.dencooper.pnstore.service;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dencooper.pnstore.domain.Role;
import com.dencooper.pnstore.domain.User;
import com.dencooper.pnstore.repository.RoleRepository;
import com.dencooper.pnstore.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User handleCreateUser(User user) {
        return this.userRepository.save(user);
    }

    public User handleFetchUserById(long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }

    public List<User> handleFetchAllUsers() {
        return this.userRepository.findAll();
    }

    public User handleUpdateUser(User user) {
        Optional<User> userOptional = this.userRepository.findById(user.getId());
        if (userOptional.isPresent()) {
            User updatedUser = userOptional.get();
            updatedUser.setEmail(user.getEmail());
            updatedUser.setFullName(user.getFullName());
            updatedUser.setAddress(user.getAddress());
            updatedUser.setPhone(user.getPhone());
            updatedUser.setAvatar(user.getAvatar());
        }
        return null;
    }

    public void handleDeleteUserById(long id) {
        this.userRepository.deleteById(id);
    }

    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }
}
