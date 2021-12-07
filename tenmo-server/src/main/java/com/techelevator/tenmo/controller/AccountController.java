package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountsDao;
//import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    @Autowired
    AccountsDao accountsDao;
<<<<<<< HEAD
    @Autowired
    UserDao userDao;
=======
//    @Autowired
//    UserDao userDao;
>>>>>>> c77c7cc4b61b72304bc8205776da987095c942c6

    @RequestMapping(path = "accounts/{id}", method = RequestMethod.GET)
    public Account getBalance(@PathVariable int id) {

//        System.out.println(accountsDao.getBalance(id));
        return accountsDao.getBalance(id);
    }

}
