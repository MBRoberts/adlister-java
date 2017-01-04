package com.codeup.adlister.dao;

import com.codeup.adlister.models.User;

public interface Users {

    User findByUsername(String username);

    User findByUserId(Long id);

    Long insert(User user);

    int update(User user);
}
