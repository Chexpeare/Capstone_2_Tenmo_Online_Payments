package com.techelevator.tenmo.controller;

<<<<<<< HEAD
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
=======
import com.techelevator.tenmo.dao.JdbcUsersDao;
import com.techelevator.tenmo.dao.UsersDao;
import com.techelevator.tenmo.model.Users;
>>>>>>> c77c7cc4b61b72304bc8205776da987095c942c6
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
<<<<<<< HEAD
    UserDao userDao;

    @RequestMapping(path = "users", method = RequestMethod.GET)
    public List<User> findAll() {

        return userDao.findAll();
    }
=======
    UsersDao usersDao;

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(path="users/", method = RequestMethod.GET)
    public List<Users> allUsers() {

        System.out.println();
        return usersDao.findAll();
    }

>>>>>>> c77c7cc4b61b72304bc8205776da987095c942c6
}
