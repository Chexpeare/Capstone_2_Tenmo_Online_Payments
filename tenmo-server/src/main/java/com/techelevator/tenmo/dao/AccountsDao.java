package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

public interface AccountsDao {

    public Account getBalance(int id);

}