package com.taxisimpledrive.taxiuserservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "verification_tokens")
public class VerificationToken {
    @Id
    private UUID id;

    @Column(nullable = false, unique = true, name = "token")
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDateTime expiryDate;
}