package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Users;

import java.util.List;

public interface UsersDao {

    List<Users> findAll();

    int findIdByUsername(String username);

    Users findByUsername(String username);

    boolean create(String username, String password);
}
