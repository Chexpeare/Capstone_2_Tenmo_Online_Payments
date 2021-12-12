package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountsDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@PreAuthorize("isAuthenticated()")

public class AccountController {

    private AccountsDao accountsDao;

    public AccountController(AccountsDao accountsDao) {
        this.accountsDao = accountsDao;
    }

    @RequestMapping(path = "account/{id}", method = RequestMethod.GET)
    public Account getAccount(@PathVariable long userid) {

        Account account = accountsDao.getAccount(userid);
        return account;
    }

    @RequestMapping(path = "balance/{id}", method = RequestMethod.GET)
    public BigDecimal getAccountBalance(@PathVariable long id) {

        BigDecimal balance = accountsDao.getAccount(id).getBalance();
        return balance;
    }

}