package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {

    private long accountId;
    private long userId;
    private static BigDecimal balance;

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

    public static BigDecimal getBalance() {
        return balance;
    }
    public static void setBalance(BigDecimal balance) {
        Account.balance = balance;
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