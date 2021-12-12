package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UsersDao;
import com.techelevator.tenmo.model.Users;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")

public class UserController {

    private UsersDao usersDao;

    public UserController(UsersDao usersDao) {
        this.usersDao = usersDao;
    }


    @RequestMapping(path = "transfer/users", method = RequestMethod.GET) //this method was not working for some reason
    public List<Users> findAll(){

        List<Users> listAllUsers = usersDao.findAll();

        return listAllUsers;

    }


}