package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    Transfer createTransfer(long userTo_id, long userFrom_id, BigDecimal amount, int transferTypeId, int transferStatusId);

    Transfer getSingleTransfer(long transfer_id);

    List <Transfer> getAllTransfers(long user_id);

    boolean runTransaction(Transfer transfer);

    void addToBalance(long user_id,BigDecimal amount);

    void subtractFromBalance(long user_id,BigDecimal amount);

    BigDecimal getAccountBalance(long user_id);

    void deleteTransfer(Long transferID);

    void requestMoney(int userTo_id, int userFrom_id, BigDecimal amount);

}