package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountsDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    @Autowired
    TransferDao transferDao;
    @Autowired
    AccountsDao accountsDao;

    @RequestMapping(path = "transfers", method = RequestMethod.POST)
    public Transfer createTransfer(@RequestBody Transfer transfer) {

//        Account fromAccount = new Account();
//        Account toAccount = new Account();
//        fromAccount.setAccountId(transfer.getAccountFrom());
//        toAccount.setAccountId(transfer.getAccountTo());

//        double newToBalance = toAccount.getBalance() + transfer.getAmount();
//        double newFromBalance = toAccount.getBalance() - transfer.getAmount();
//        toAccount.setBalance(newToBalance);
//        fromAccount.setBalance(newFromBalance);

        transferDao.createTransfer(transfer);
//        accountsDao.save(fromAccount);
//        accountsDao.save(toAccount);
//        return transferDao.save(transfer);
        return transfer;
    }

}
