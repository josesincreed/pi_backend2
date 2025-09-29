package com.example.pib2.service;

import com.example.pib2.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    void deleteById(Long id);
}
