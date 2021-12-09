package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    List<Transfer> listOfTransfers();

    boolean createTransfer(Transfer transfer);

//    Transfer createTransfer(long from_user_id, long to_user_id, BigDecimal amount);

}
