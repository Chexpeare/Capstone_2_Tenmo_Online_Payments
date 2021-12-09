package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {

    private final String API_BASE_URL;
    private final RestTemplate restTemplate = new RestTemplate();
    private final AuthenticatedUser currentUser;

    public AccountService(String API_BASE_URL, AuthenticatedUser currentUser) {
        this.API_BASE_URL = API_BASE_URL;
        this.currentUser = currentUser;
    }

    public Account getAccount(long accountId){
        Account currentAccount = null;

        try {
            ResponseEntity<Account> response =
                    restTemplate.exchange(API_BASE_URL + "accounts/" + accountId,
                            HttpMethod.GET, makeAuthEntity(), Account.class);
            currentAccount = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            System.out.println("Error finding transfer.");
        }
        return currentAccount;
    }

    public Account getAccountBalance() {
        Account accountBalance = null;
        try {
            ResponseEntity<Account> responseBalance = restTemplate.exchange(API_BASE_URL + "accounts/" + currentUser.getUser().getId(),
                            HttpMethod.GET, makeAuthEntity(), Account.class);
            accountBalance = responseBalance.getBody();

        } catch (RestClientException e) {
            System.out.println("Error getting balance " + e.getMessage());
        }
        return accountBalance;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        return new HttpEntity<>(headers);
    }

}