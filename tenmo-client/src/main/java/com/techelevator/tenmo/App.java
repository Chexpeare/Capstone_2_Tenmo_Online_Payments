package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.TenmoService;
import com.techelevator.view.ConsoleService;

import java.math.BigDecimal;

public class App {

	private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String TRANSFER_AMOUNT = "Enter the amount you would like to transfer";

	private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;

    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
    	app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");
		
		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
		// TODO !!==IMPLEMENTATION_IN_PROGRESS==!! : viewCurrentBalance()
		BigDecimal balance = Balance.getBalance(currentUser.getToken());

		System.out.println("viewCurrentBalance(): $" + balance + " TE Bucks");
		
	}

	private void viewTransferHistory() {
		// TODO !!==IMPLEMENTATION_IN_PROGRESS==!! : viewTransferHistory()
		System.out.println("viewTransferHistory(): IMPLEMENTATION_IN_PROGRESS");

		try {
			boolean valid = false;
			while (!valid) {
				int selectUser = Integer.parseInt(console.getUserInput("\t(1) - View all transfers for your account (Long Format)" +
						"\n\t(2) - View all transfers for your account (Short Format)" +
						"\n\t(3) - View the details of one transfer (Requires a Transfer ID)" + "\n\nPlease choose an option >>> "));
				TenmoService tenmoService = null;
				if (selectUser == 1) {
					Transfer[] transfers = tenmoService.listAllTransfers(currentUser.getToken(), currentUser.getUser().getId());
					for (Transfer i : transfers) {
						System.out.println("------------------------------------------");
						System.out.println(" Transfer Id: " + i.getTransferId() + "\t\t  Amount: $" + i.getAmount());
						System.out.println("\t\tSender Acct.:\t    " + i.getAccountFrom());
						System.out.println("\t\tRecipient Acct.:    " + i.getAccountTo());
						System.out.println("------------------------------------------");
					}
					console.getUserInput("Press enter key to continue");
					valid = true;
				} else if (selectUser == 2) {
					Transfer[] transfers = tenmoService.listAllTransfers(currentUser.getToken(),
							currentUser.getUser().getId());
					for (Transfer i : transfers) {
						System.out.println(" Id: " + i.getTransferId() + " * To: "
								+ i.getAccountTo() + " * From: " + i.getAccountFrom() +
								" * Amount: $" + i.getAmount());
					}
					console.getUserInput("Press enter key to continue");
					valid = true;
				} else if (selectUser == 3) {

					int transferId = Integer.parseInt(console.getUserInput("Enter the transfer ID: "));
					Transfer transfer = tenmoService.singleTransfer(currentUser.getToken(), transferId);
					System.out.println("------------------------------------------");
					System.out.println(" Transfer Id: " + transfer.getTransferId() + "\t\t  Amount: $" + transfer.getAmount());
					System.out.println("\t\tSender Acct.:\t    " + transfer.getAccountFrom());
					System.out.println("\t\tRecipient Acct.:    " + transfer.getAccountTo());
					System.out.println("------------------------------------------");
					console.getUserInput("Press enter key to continue");
					valid = true;
				} else {
					System.out.println("Invalid Selection!");
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		System.out.println("viewPendingRequests(): not yet implemented.");

	}

	private void sendBucks() {
		// TODO Auto-generated method stub
		System.out.println("sendBucks(): not yet implemented.");

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
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
				Balance.setBalance(BigDecimal.valueOf(1000));
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
            }
        }
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}
	
	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
}
