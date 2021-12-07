package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcUsersDao;
import com.techelevator.tenmo.dao.UsersDao;
import com.techelevator.tenmo.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {

    @Autowired
    UsersDao usersDao;

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(path="users/", method = RequestMethod.GET)
    public List<Users> allUsers() {

        System.out.println();
        return usersDao.findAll();
    }

}
