package com.canevi.user.auth.web.controller;

import com.canevi.user.auth.infrastructure.entity.User;
import com.canevi.user.auth.service.JwtService;
import com.canevi.user.auth.service.UserService;
import com.canevi.user.auth.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private UserDetailsService userDetailsService;
    private JwtService jwtService;

    @PostMapping
    public User registerUser(@RequestBody UserDTO userDTO) {
        return userService.registerUser(userDTO);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestParam String username, @RequestParam String password) {
        Optional<User> userOpt = userService.authenticateUser(username, password);

        if (userOpt.isPresent()) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String token = jwtService.generateToken(userDetails);
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }


    @GetMapping("/detail/{userId}")
    public Optional<User> getUserDetails(@PathVariable String userId) {
        return userService.getUserDetails(userId);
    }
}