package com.canevi.user.auth.service;

import com.canevi.user.auth.infrastructure.entity.User;
import com.canevi.user.auth.infrastructure.repository.UserRepository;
import com.canevi.user.auth.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User registerUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        User user = userDTO.mapToEntity();
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        return userRepository.save(user);
    }

    public Optional<User> authenticateUser(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    public Optional<User> getUserDetails(String userId) {
        return userRepository.findById(userId);
    }
}

