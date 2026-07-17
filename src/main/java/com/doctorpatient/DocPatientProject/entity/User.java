package com.doctorpatient.DocPatientProject.entity;

import com.doctorpatient.DocPatientProject.entity.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private Integer failedAttempts = 0;

    @Column(nullable = false)
    private Boolean accountLocked = false;

    private LocalDateTime lockTime;
}
