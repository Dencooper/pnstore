package com.dencooper.pnstore.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dencooper.pnstore.domain.Role;
import com.dencooper.pnstore.domain.User;
import com.dencooper.pnstore.domain.dto.RegisterDTO;
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

    public User handleSaveUser(User user) {
        return this.userRepository.save(user);
    }

    public User fetchUserById(long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }

    public Page<User> fetchAllUsers(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    public void handleDeleteUserById(long id) {
        this.userRepository.deleteById(id);
    }

    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }

    public boolean checkExistEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public User fetchUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User handelResDTOToUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        return user;
    }

    public long countUser() {
        return this.userRepository.count();
    }
}
