package com.techelevator.tenmo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {

    @Id
    private long accountId;
    private long userId;
    private double balance;

    public Account(){}

    public Account(long accountId, long userId, double balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
    }

    public long getAccountId() {
        return accountId;
    }
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "account_id=" + accountId +
                ", user_id=" + userId +
                ", balance=" + balance +
                '}';
    }
}
