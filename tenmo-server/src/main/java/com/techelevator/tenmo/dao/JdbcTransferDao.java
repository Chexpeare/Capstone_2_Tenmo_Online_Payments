package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Users;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {


    @Override
    public List<Users> listOfUsers() {
        return null;
    }
}
