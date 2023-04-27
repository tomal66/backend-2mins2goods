package com.mins2goods.backend.service;

import com.mins2goods.backend.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User getUser(String username);
    List<User> getUsers();
}
