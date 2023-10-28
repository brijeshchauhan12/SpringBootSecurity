package com.springsecurity.springsecurityclient.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firtName;
    private String lastName;

    private String email;
    @Column(length = 60)
    private String password;

    private  String role;

    private boolean enabled=false;

}
