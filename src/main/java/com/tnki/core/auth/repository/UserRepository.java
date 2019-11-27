package com.tnki.core.auth.repository;

import com.tnki.core.auth.model.User;

public interface UserRepository {
    User findByUsername(String username);

    void insertUser(User user);
}
