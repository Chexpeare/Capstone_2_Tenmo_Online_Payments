package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsDao {

    public Account getBalance(int id);

}