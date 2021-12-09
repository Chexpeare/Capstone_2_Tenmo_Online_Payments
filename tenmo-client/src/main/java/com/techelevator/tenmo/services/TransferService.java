package com.techelevator.tenmo.services;

import com.techelevator.tenmo.App;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.view.ConsoleService;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class TransferService {
    private final String API_BASE_URL;
    private final AuthenticatedUser currentUser;
    private final RestTemplate restTemplate = new RestTemplate();
    private ConsoleService consoleService;
    private AuthenticationService authenticationService;


    public TransferService(String API_BASE_URL, AuthenticatedUser currentUser) {
        this.API_BASE_URL = API_BASE_URL;
        this.currentUser = currentUser;
    }

    public User[] getListofUsers() {
        User[] users = null;
        try {
            users = restTemplate.exchange(API_BASE_URL + "/users", HttpMethod.GET, makeAuthEntity(), User[].class).getBody();

        } catch (RestClientResponseException e) {
            System.out.println("Error getting users" + e.getMessage());
        }
        return users;
    }

    public User getUserFromChoiceForTransfer() {
        User recipient = null;
        ConsoleService consoleService = new ConsoleService(System.in, System.out);
        User[] users = null;
        System.out.println("Which user would you like to send TE Bucks to?>>>");
        users = restTemplate.exchange(API_BASE_URL + "users/", HttpMethod.GET,
                makeAuthEntity(), User[].class).getBody();
        String[] userNames = new String[users.length];
        for (int i = 0; i < users.length; i++) {
            String name = users[i].getUsername();
            userNames[i] = name;
        }
        String choice = (String) consoleService.getChoiceFromOptions(userNames);
        recipient = restTemplate.exchange(API_BASE_URL + "users/" + choice, HttpMethod.GET, makeAuthEntity(), User.class).getBody();
        System.out.println(recipient.getId().toString());
        return recipient;
    }

    public Transfer transferFunds() {
        AccountService accountServices = new AccountService(API_BASE_URL, currentUser);
        Scanner userInput = new Scanner(System.in);
        User recipient = getUserFromChoiceForTransfer();
        String choice = null;
        Transfer transfer = null;

        Account account = accountServices.getAccountBalance();
        System.out.println("Your current balance is: " + account.getBalance() + " TE Bucks.");
        System.out.println("How many TE bucks would you like to send?>>>");
        String transferAmountString = userInput.nextLine();
        BigDecimal transferAmount = new BigDecimal(transferAmountString);
        BigDecimal bdZero = new BigDecimal(0);

        if (transferAmount.compareTo(account.getBalance()) == 1) {
            System.out.println("Insufficient funds.  Please check your balance.");
            transferFunds();
        } else if (transferAmount.compareTo(bdZero) == 0){
            System.out.println("You can't transfer 0 TE bucks.  Please try again.");
            transferFunds();
        } else {
            System.out.println("Please confirm transfer:");
            System.out.println("------------------------------------");
            System.out.println("Transfer: " + transferAmount + " TE.X");
            System.out.println("To: " + recipient.getUsername());
            System.out.print("(Y/N)>>>");
            choice = userInput.nextLine();
        }
        if (choice.toLowerCase().equals("n")) {
            System.out.println("Cancelling transfer. . . ");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("What?");

            }
            System.out.println("Returning to main menu. . .");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("What?");

            }
//            App.mainMenu();

        } else {
            transfer = new Transfer(2, 2, currentUser.getUser().getId(), recipient.getId(), transferAmount);
            //System.out.println("TEST" + transfer.toString());
            HttpEntity<Transfer> transferEntity = new HttpEntity<>(transfer, makeAuthEntity().getHeaders());

            try {
                Transfer newTransfer = restTemplate.exchange(API_BASE_URL + "transfers", HttpMethod.POST, makeTransferEntity(transfer), Transfer.class).getBody();
            } catch (Exception e) {
                System.out.println("Something went wrong.  Try again.");
//                App.mainMenu();
            }
            System.out.println("Successful Transfer!");
        }
        return transfer;

    }
    public List<Transfer> listTransferHistory(int userId) {
        List<Transfer> transferHistory = null;

        //try {
        transferHistory = restTemplate.exchange(API_BASE_URL + "transfers/history/" + userId, HttpMethod.GET, makeAuthEntity(), List.class).getBody();
        //} catch (RestClientResponseException e) {
        //    System.out.println("Not sure, but you got this far.");
        // }

        return transferHistory;
    }

    public Transfer getTransferDetails(int transferId) {
        Transfer transferDetails = restTemplate.exchange(API_BASE_URL + "transfers/" + transferId, HttpMethod.GET, makeAuthEntity(), Transfer.class).getBody();

        return transferDetails;
    }

    public Transfer createTransfer(int userIdFrom, int userIdTo, double amount) {

        Transfer newTransfer = new Transfer();

        newTransfer.setAccountFromId(userIdFrom);
        newTransfer.getAccountToId(userIdTo);
        newTransfer.setTransferAmount(BigDecimal.valueOf(amount));

        try {
            newTransfer = restTemplate.postForObject(API_BASE_URL + "transfers", makeTransferEntity(newTransfer), Transfer.class);

        } catch (RestClientResponseException e) {

            BigDecimal zero = new BigDecimal(0);

            if(newTransfer.getTransferAmount().compareTo(zero) == 1) {
                System.out.println("Transfer failed");
            } else {
                System.out.println("Entered amount must be a positive amount.");
            }

        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());

        } catch (NullPointerException f) {
            System.out.println("Caught null pointer exception in create transfer in transfer service");
        }
        return newTransfer;
    }

    private HttpEntity makeAuthEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity<Transfer> transferHttpEntity = new HttpEntity<>(transfer, headers);
        return transferHttpEntity;
    }

}