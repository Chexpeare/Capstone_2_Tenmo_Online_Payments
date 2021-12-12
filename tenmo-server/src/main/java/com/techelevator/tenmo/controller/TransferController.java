package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")

public class TransferController {

    private TransferDao transferDao;

    public TransferController (TransferDao transferDao) {
        this.transferDao = transferDao;
    }

    @RequestMapping(path = "transfers", method = RequestMethod.POST)
    public Transfer createTransfer(@RequestBody Transfer transfer) {

        Transfer createdTransfer = transferDao.createTransfer(transfer.getUser_id_From(),transfer.getUser_id_To(),transfer.getAmount());

        return createdTransfer;
    }

    @RequestMapping(path = "transfers/{transferID}", method = RequestMethod.GET)//make a transfer not found exception
    public Transfer getSingleTransfer(@PathVariable long transferID){

        Transfer singleTransfer = transferDao.getSingleTransfer(transferID);
        return singleTransfer;
    }
    @RequestMapping(path = "transfers/{userID}/all", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers(@PathVariable long userID) {

        List<Transfer> listAllTransfers = transferDao.getAllTransfers(userID);

        return listAllTransfers;
    }

}