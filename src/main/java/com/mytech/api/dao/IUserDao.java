package com.mytech.api.dao;

import com.mytech.api.models.User;

import java.util.List;

public interface IUserDao {

    List<User> getUsers();
    void Delete(Long id);

    void RegisterUser(User user);

    User GetUserWithCredentials(User user);
}
