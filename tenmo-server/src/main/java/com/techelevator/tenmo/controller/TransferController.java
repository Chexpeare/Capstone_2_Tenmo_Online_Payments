package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UsersDao;
import com.techelevator.tenmo.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    @Autowired
    TransferDao transferDao;
    @Autowired
    UsersDao usersDao;

//    public TransferController(TransferDao transferDao, UserDao userDao) {
//        this.transferDao = transferDao;
//        this.userDao = userDao;
//    }

    @RequestMapping(path = "users/", method = RequestMethod.GET)
    public List<Users> getAllUsers() throws AuthenticationServiceException {

        return usersDao.findAll();
    }

}
