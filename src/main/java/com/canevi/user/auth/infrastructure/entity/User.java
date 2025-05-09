package com.canevi.user.auth.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String userId;
    @Column(unique = true)
    private String username;
    @Column
    private String password;
    @Column
    private String fullName;
    @Column
    private String email;
    @Column
    private String role;
}
