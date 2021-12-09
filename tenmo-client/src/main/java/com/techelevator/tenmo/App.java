package com.techelevator.tenmo;

import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.services.TransferService;
import com.techelevator.view.ConsoleService;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;

import java.math.BigDecimal;

public class App {

	private static final String API_BASE_URL = "http://localhost:8080/";

    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String MENU_OPTION_EXIT = "Exit";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
//	private static final String TRANSFER_AMOUNT = "Enter the amount you would like to transfer";

	private AuthenticatedUser currentUser;
	private ConsoleService console;
	private AuthenticationService authenticationService;
	private AccountService accountService;

	public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
	}

	public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
    	app.run();
    }

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");

		registerAndLogin();
		mainMenu();
	}

	public void mainMenu() {
		try {
			while(true) {

				String choice = (String) console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
				if (MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
					viewCurrentBalance();
				} else if (MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
					sendBucks();
				} else if (MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
					viewTransferHistory();
				} else if (MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
					requestBucks();
				} else if (MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
					viewPendingRequests();
				} else if (MAIN_MENU_OPTION_LOGIN.equals(choice)) {
					login();
				} else {
					// the only other option on the main menu is to exit
					exitProgram();
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
	}

	private void viewCurrentBalance() {
		// TODO !!==COMPLETED==!! : viewCurrentBalance()
		try {
			console.displayToConsole("Your current account balance is: $" + accountService.getAccountBalance().getBalance());
		} catch (NullPointerException e) {
			System.out.println("Account empty.");
		}
}

	private void viewTransferHistory() {
		// TODO !!==IMPLEMENTATION_IN_PROGRESS==!! : viewTransferHistory()
		System.out.println("viewTransferHistory(): IMPLEMENTATION_IN_PROGRESS");
//		TransferService transferService = new TransferService(API_BASE_URL, currentUser);

		try {
//			transferService.getAllTransfers();
		} catch (NullPointerException e) {
			System.out.println("No transfers found");
		}

		Integer enteredTransferID = console.getUserInputInteger("Please enter transfer ID to view details (0 to cancel): ");
		try {
			if (enteredTransferID == 0) {
				mainMenu();
			}
//			transferService.getSingleTransfer(enteredTransferID);
		} catch (NullPointerException e) {
			System.out.println("Transfer ID Invalid.");
		} catch (NumberFormatException e) {
			System.out.println("Transfer ID Invalid.");
		}
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		System.out.println("viewPendingRequests(): not yet implemented.");
	}

	private void sendBucks() {
		// TODO !!==IMPLEMENTATION_IN_PROGRESS==!! : sendBucks()
		System.out.println("\n***** sendBucks(): IMPLEMENTATION_IN_PROGRESS. *****");
		System.out.println("---------------------------------");
		System.out.println("Users");
		System.out.println("ID          Name");
		System.out.println("---------------------------------");

		TransferService transferService = new TransferService(API_BASE_URL, currentUser);
		try {
			for (User user : transferService.getListofUsers()) {
				console.displayToConsole(user.toString() + "\n");
			}
		} catch (NullPointerException e) {
			System.out.println("Account empty.");
		}

		Integer enteredUserID = console.getUserInputInteger("---------------------------------\n" +
				"Enter ID of user you are sending to (0 to cancel): ");
		if (enteredUserID == 0) {
			mainMenu();
		}

		double enteredAmount = console.getUserInputAmount("Enter Transfer Amount: ");
		try {
			Transfer newTransfer = new Transfer();
			Transfer newTransferCheck = new Transfer();
			newTransfer = transferService.createTransfer(currentUser.getUser().getId(), enteredUserID, enteredAmount);
//			newTransferCheck = transferService.getSingleTransfer(newTransfer.getTransferId());
//
//			if (!newTransferCheck.equals(null)) {
//
//				System.out.println("Transfer successfully processed.");
//			}
//
//			if (newTransferCheck.equals(null)) {
//				System.out.println("Transfer failed.");
//			}

		} catch (NullPointerException f){
			System.out.println(f.getMessage());

//			if(enteredAmount.compareTo(accountService.getAccountBalance().getBalance()) == 1) {
//				System.out.println("Not enough balance." + f.getMessage());
//			}

		} catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		System.out.println("requestBucks(): not yet implemented.");

	}

	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				exitProgram();	// the only other option on the login menu is to exit
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account.");
		boolean isRegistered = false;
		while (!isRegistered) //will keep looping until user is registered
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				authenticationService.register(credentials);
				isRegistered = true;
				System.out.println("Registration successful. You can now login.");
			} catch (AuthenticationServiceException e) {
				System.out.println("REGISTRATION ERROR: "+ e.getMessage());
				System.out.println("Please attempt to register again.");
			}
		}
	}

	private void login() {
		System.out.println("Please log in.");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
				accountService = new AccountService(API_BASE_URL, currentUser);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: " + e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}

	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username: ");
		String password = console.getUserInput("Password: ");
		return new UserCredentials(username, password);
	}
}
