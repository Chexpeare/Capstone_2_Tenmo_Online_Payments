package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Users;

import java.util.List;

public interface UsersDao {

    List<Users> findAll();

    Users findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password);
}