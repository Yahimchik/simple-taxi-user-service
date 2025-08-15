package com.taxisimpledrive.taxiuserservice.model;

import com.taxisimpledrive.taxiuserservice.model.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users")
public class User {
    @Id
    private UUID id;

    @Column(unique = true, nullable = false, name = "phone_number")
    private String phoneNumber;

    @Column(unique = true, nullable = false, name = "email")
    private String email;

    @Column(nullable = false, name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false, name = "last_visit")
    private LocalDateTime lastVisit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    private UserStatus status;
}