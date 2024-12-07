package com.example.lab5project2.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "myusers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUser {
    @Id
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String country;
    private String role;
    private boolean enabled = true;
    private boolean accountNonExpired = true;
    private boolean credentialsNonExpired = true;
    private boolean accountNonLocked = true;

}
