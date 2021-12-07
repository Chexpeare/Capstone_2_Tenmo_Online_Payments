package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class TransferService {

    private String API_BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;
    private int userIdFrom;
    private int userIdTo;

    public TransferService(String API_BASE_URL, AuthenticatedUser currentUser) {
        this.API_BASE_URL = API_BASE_URL;
        this.currentUser = currentUser;
    }

    public User[] getListofUsers() {
        User[] users = null;
        try {
<<<<<<< HEAD
         users = restTemplate.exchange(API_BASE_URL + "/users", HttpMethod.GET, makeAuthEntity(), User[].class).getBody();
=======
            users = restTemplate.exchange(API_BASE_URL + "users", HttpMethod.GET, makeAuthEntity(), List.class).getBody();
//            ResponseEntity<User> listOfUsers = restTemplate.exchange(API_BASE_URL + "/users", HttpMethod.GET, makeAuthEntity(), User.class);
>>>>>>> c77c7cc4b61b72304bc8205776da987095c942c6

        } catch (RestClientResponseException e) {
            System.out.println("Error getting users" + e.getMessage());
        }
        return users;
    }

    //Get single Transfer by Transfer ID
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

            System.out.println("Id: " + currentTransfer.getTransfer_id());
            System.out.println("From: " + currentTransfer.getAccount_from());
            System.out.println("To: " + currentTransfer.getAccount_to());
            System.out.println("Type: " + currentTransfer.getTransfer_type_id());
            System.out.println("Status: " + currentTransfer.getTransfer_status_id());
            System.out.println("Amount: " + currentTransfer.getAmount());
            System.out.println("");
        } catch (RestClientResponseException | ResourceAccessException e) {
            System.out.println("Error finding transfer.");
        }
        return currentTransfer;
    }

    //Get all Transfers by UserID

    public Transfer[] getAllTransfers() {

        Transfer[] allTransfers = null;

        try {

            System.out.println("-------------------------------------------");
            System.out.println("Transfers");
            System.out.println("ID          From/To                 Amount");
            System.out.println("-------------------------------------------");

            allTransfers = restTemplate.exchange(API_BASE_URL + currentUser.getUser().getId() + "/all", HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();
            for (Transfer i : allTransfers) {
                if (i.getTransfer_type_id() == 2) {
                    System.out.printf("%s  %13s  %20s \n", i.getTransfer_id(), "To: " + i.getAccount_to(), "$ " + i.getAmount());
                }
                else {
                    System.out.printf("%s  %13s  %20s \n", i.getTransfer_id(), "From: " + i.getAccount_from(), "$ " + i.getAmount());
                }
            }
            System.out.println("");
        } catch(RestClientResponseException e){
            System.out.println("Error getting users");
        }
        return allTransfers;

    }

    public Transfer createTransfer(long user_id_from, long user_id_to, BigDecimal amount) {

        Transfer newTransfer = new Transfer();

        newTransfer.setAccount_from(userIdFrom);
        newTransfer.setAccount_to(userIdTo);
        newTransfer.setAmount(amount);

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