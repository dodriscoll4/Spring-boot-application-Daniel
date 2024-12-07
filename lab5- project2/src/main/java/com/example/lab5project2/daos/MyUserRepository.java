package com.example.lab5project2.daos;


import com.example.lab5project2.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyUserRepository extends JpaRepository<MyUser, String> {

}
