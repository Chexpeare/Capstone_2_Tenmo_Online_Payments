package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountsDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    UserDao userDao;

//    public TransferController(TransferDao transferDao, UserDao userDao) {
//        this.transferDao = transferDao;
//        this.userDao = userDao;
//    }

    @RequestMapping(path = "users/", method = RequestMethod.GET)
    public List<User> getAllUsers() throws AuthenticationServiceException {

        return userDao.findAll();
    }

}
