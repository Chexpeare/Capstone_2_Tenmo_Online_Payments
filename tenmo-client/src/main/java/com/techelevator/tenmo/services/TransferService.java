package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class TransferService {

    private String API_BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;

    public TransferService(String API_BASE_URL, AuthenticatedUser currentUser) {
        this.API_BASE_URL = API_BASE_URL;
        this.currentUser = currentUser;
    }

    public Transfer getSingleTransfer(long transferID) {
        Transfer currentTransfer = null;

        try {
            ResponseEntity<Transfer> response =
                    restTemplate.exchange(API_BASE_URL + "transfers/" + transferID,
                            HttpMethod.GET, makeAuthEntity(), Transfer.class);
            currentTransfer = response.getBody();
            System.out.println("");
            if(!currentTransfer.equals(null)) {
                System.out.println("--------------------------------------------");
                System.out.println("Transfer Details");
                System.out.println("--------------------------------------------");
            }

            System.out.println("Id: " + currentTransfer.getTransferId());
            System.out.println("From: " + currentTransfer.getUsernameFrom());
            System.out.println("To: " + currentTransfer.getUsernameTo());
            System.out.println("Type: " + currentTransfer.getTransferType());
            System.out.println("Status: " + currentTransfer.getTransferStatus());
            System.out.println("Amount: " + currentTransfer.getAmount());
            System.out.println("--------------------------------------------\n");

        } catch (RestClientResponseException | ResourceAccessException e) {
            System.out.println("Error finding transfer.");
        }
        return currentTransfer;
    }

    public Transfer[] getAllTransfers() {

        Transfer[] allTransfers = null;

        try {
            printTransferHeader();
            allTransfers = restTemplate.exchange(API_BASE_URL + "transfers/" + currentUser.getUser().getId() + "/all", HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();
            for (Transfer i : allTransfers) {
                if (i.getTransferTypeId() == 2) {
                    System.out.printf("%s  %13s  %20s \n", i.getTransferId(), "To: " + i.getUsernameTo(), "$ " + i.getAmount());
                }
                else {
                    printTransferHeader();
                    System.out.printf("%s  %13s  %20s \n", i.getTransferId(), "From: " + i.getUsernameFrom(), "$ " + i.getAmount());
                    printTransferFooter();
                }
            }
            printTransferFooter();
            System.out.println("");
        } catch(RestClientResponseException e){
            System.out.println("Error getting users");
        }
        return allTransfers;

    }

    public Transfer createTransfer(long userIdFrom, long userIdTo, BigDecimal amount, int transferTypeId, int transferStatusId) {

        Transfer newTransfer = new Transfer();

        newTransfer.setUser_id_From(userIdFrom);
        newTransfer.setUser_id_To(userIdTo);
        newTransfer.setAmount(amount);
        newTransfer.setTransferTypeId(transferTypeId);
        newTransfer.setTransferStatusId(transferStatusId);

        try {

            newTransfer = restTemplate.postForObject(API_BASE_URL + "transfers", makeTransferEntity(newTransfer), Transfer.class);

        } catch (RestClientResponseException e) {

            BigDecimal zero = new BigDecimal(0);

            if(newTransfer.getAmount().compareTo(zero) == 1) {

                System.out.println("Transfer failed");
            }else{
                System.out.println("Entered amount must be a positive amount.");
            }

        } catch (ResourceAccessException e) {
            System.out.println(e);

        } catch (NullPointerException f){
            System.out.println("Caught null pointer exception in create transfer in transfer service");
        }

        return newTransfer;
    }

    private void printTransferHeader() {
        System.out.println("-------------------------------------------");
        System.out.println("Transfers");
        System.out.println("ID          From/To                 Amount");
        System.out.println("-------------------------------------------");

    }
    private void printTransferFooter() {
        System.out.println("-------------------------------------------");
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(currentUser.getToken());
        return new HttpEntity<>(transfer, headers);
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        return new HttpEntity<>(headers);


    }

}