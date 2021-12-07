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

public class UserService {

    private String API_BASE_URL;
    private final RestTemplate restTemplate = new RestTemplate();
    private final AuthenticatedUser currentUser;

    public UserService(String API_BASE_URL, AuthenticatedUser currentUser) {
        this.API_BASE_URL = API_BASE_URL;
        this.currentUser = currentUser;
    }

    public User getId(long userId){
        User currentUser = null;
        try {
            ResponseEntity<User> response =
                    restTemplate.exchange(API_BASE_URL + "users/" + userId,
                            HttpMethod.GET, makeAuthEntity(), User.class);
            currentUser = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            System.out.println("Error finding users.");
        }
        return currentUser;
    }

    public User getUsername(String username){
        User currentUser = null;
        try {
            ResponseEntity<User> response =
                    restTemplate.exchange(API_BASE_URL + "users/" + username,
                            HttpMethod.GET, makeAuthEntity(), User.class);
            currentUser = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            System.out.println("Error finding users.");
        }
        return currentUser;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        return new HttpEntity<>(headers);
    }

}