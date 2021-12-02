package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Balance {

    private static BigDecimal balance;
//    private BigDecimal balance;
    private BigDecimal transferAmount;

    public static BigDecimal getBalance(String token) {
        return balance;
    }

    public static void setBalance(BigDecimal balance) {
        Balance.balance = balance;
    }

}