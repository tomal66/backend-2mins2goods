package com.mins2goods.backend.service;

import com.mins2goods.backend.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    User getUser(String username);
    List<User> getUsers();

    Optional<Object> getUserById(Long userId);
}
