package com.canevi.user.auth.web.dto;

import com.canevi.user.auth.infrastructure.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDTO {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String role;

    public User mapToEntity() {
        return new User(UUID.randomUUID().toString(), username, password, fullName, email, role);
    }
}
