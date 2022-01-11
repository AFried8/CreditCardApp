import java.time.LocalDate;
import java.util.Calendar;
import java.util.Scanner;

public class ManageCreditCards {
	
	Scanner input = new Scanner(System.in);
	CreditCards myCards;
	Menu mainMenu;
	Menu manageWallet;
	Menu financialStats;
	Menu manageCard;
	
	public ManageCreditCards() {
		myCards = new CreditCards();
		createMenus();
		mainMenu();
	}
	
	private void createMenus() {
		mainMenu = new Menu("Main Menu");
		mainMenu.addOption("Manage my wallet");
		mainMenu.addOption("See my financial stats");
		mainMenu.addOption("Manage specific card");
		mainMenu.addOption("Exit");
		
		manageWallet = new Menu("Manage my wallet");
		manageWallet.addOption("Add a new credit card");
		manageWallet.addOption("Remove a credit card");
		manageWallet.addOption("Return to previous main menu");
		
		financialStats = new Menu("Financial Stats");
		financialStats.addOption("See my total balance");
		financialStats.addOption("See my total available credit");
		financialStats.addOption("See my largest purchase");
		financialStats.addOption("See my biggest spending area");
		financialStats.addOption("Return to main menu");
		
		manageCard = new Menu("Manage Card");
		manageCard.addOption("See my balance");
		manageCard.addOption("See my credit limit");
		manageCard.addOption("See my available credit");
		manageCard.addOption("Add a purchase");
		manageCard.addOption("Add a payment");
		manageCard.addOption("Add a fee");
		manageCard.addOption("See my most recent purchase");
		manageCard.addOption("See my most recent payment");
		manageCard.addOption("Return to main menu");
	}
	
	public void mainMenu() {
		int choice; 
		do {
			System.out.println(mainMenu);
			choice = input.nextInt();
			choice = validate(1, mainMenu.getMax(), choice);
			switch (choice) {
			case 1:
				manageWallet();
				break;
			case 2: 
				financialStats();
				break;
			case 3:
				manageCard();
				break;
			default:
				System.out.println("Exiting... Thank you for using this program!");
			}
		} while(choice < 4);
	}
	
	public void manageWallet() {
		int choice;
		do {
			System.out.println(manageWallet);
			choice = input.nextInt();
			choice = validate(1, manageWallet.getMax(), choice);
			switch (choice) {
			case 1:
				addCard();
				break;
			case 2:
				removeCard();
				break;
			}
		} while(choice < 3);
	}
	
	public void financialStats() {
		if(myCards.getNumberOfCards() < 1) {
			System.out.println("Sorry you don't have any financial stats since you didn't register any cards yet");
		}
		else {
			int choice;
			do {
				System.out.println(financialStats);
				choice = input.nextInt();
				choice = validate(1, financialStats.getMax(), choice);
				switch (choice) {
				case 1: 
					System.out.println("The total balance of all your cards is " + myCards.totalBalance());
					break;
				case 2:
					System.out.println("Your total available credit is " + myCards.totalAvailCredit());
					break;
				case 3:
					if(myCards.hasPurchases()) {
						System.out.println("Your largest purchase was " + myCards.largestPurchase());
					} else {
						System.out.println("There are no purchases on any card");
					}
					break;
				case 4:
					if(myCards.hasPurchases()) {
						System.out.println("Your biggest spending area is " + myCards.biggestSpendingArea());
					} else {
						System.out.println("There are no purchases on any card");
					}
					break;
				}
			} while(choice < 5);
		}
	}
	
	public void manageCard() {
		if(myCards.getNumberOfCards() < 1) {
			System.out.println("Sorry you don't have any cards to manage since you didn't register any cards. ");
		}
		else {
			int choice;
			do {
				System.out.println("Which card would you like to manage?");
				System.out.println(myCards.toString());
				int cardChoice = input.nextInt();
				cardChoice = validate(1, myCards.getNumberOfCards(), cardChoice);
				
				System.out.println(manageCard);
				choice = input.nextInt();
				choice = validate(1, manageCard.getMax(), choice);
				
				switch(choice) {
				case 1:
					System.out.println("Current balance is " + myCards.getCardBalance(cardChoice));
					break;
				case 2:
					System.out.println("Credit Limit is " + myCards.getCardCreditLimit(cardChoice));
					break;
				case 3:
					System.out.println("Available credit is " + myCards.getCardAvailCredit(cardChoice));
					break;
				case 4:
					addPurchase(cardChoice);
					break;
				case 5:
					addPayment(cardChoice);
					break;
				case 6:
					addFee(cardChoice);
					break;
				case 7:
					if(myCards.hasPurchases(cardChoice)) {
						System.out.println("Most recent purchase is " + myCards.getRecentPurchase(cardChoice));
					} else {
						System.out.println("No purchases have been made on this card");
					}
					break;
				case 8:
					if(myCards.hasPayment(cardChoice)) {
						System.out.println("Most recent payment is " + myCards.getRecentPayment(cardChoice));
					} else {
						System.out.println("No payments have been made on this card");
					}
					break;
				}
			} while(choice < 9);
		}
	}


	public int validate(int min, int max, int userInput) {
		
		while(userInput < min || userInput > max) {
			if(min == max) {
				System.out.println("You can only enter option " + min);		
			}
			else {
				System.out.println("Please enter numbers " + min + "-" + max + " as they correspond to the options");
			}
			userInput = input.nextInt();
		}
		return userInput;
	}
	
	private double validateDouble(double min, double max, double userInput) {
		while(userInput < min || userInput > max) {
			System.out.println("Please enter a number that is at least " + min + " and no more than " + max);
			userInput = input.nextDouble();
		}
		return userInput;
	}
	
	public LocalDate validateDate(String userInput,LocalDate issueDate) {
		LocalDate date = LocalDate.now(); //this date will be changed
		boolean invalidFormat = true;
		while(invalidFormat) {
			try {
			date = LocalDate.parse(userInput);
			invalidFormat = false;
			}
			catch(Exception e) {
				System.out.println("Please enter the date as yyyy-mm-dd");
				userInput = input.nextLine();
			}
		}
		
		while(date.isAfter(LocalDate.now()) || date.isBefore(issueDate)){
			System.out.println("This date is invalid. Please enter a new one");
			userInput = input.nextLine();
			date = validateDate(userInput, issueDate);
		}
		return date;
	}
	
	public LocalDate validateExpired(LocalDate date, int cardChoice) {
		while(date.isAfter(myCards.getExpirationDate(cardChoice))){
			System.out.println("Transaction date must be before expiration date. Please enter a new one");
			String userDate = input.nextLine();
			date = validateDate(userDate, myCards.getIssueDate(cardChoice) );
		} return date;
	}
	public void removeCard() {
		if(myCards.getNumberOfCards() < 1) {
			System.out.println("Sorry, there are no cards registered.");
		}
		else {
			System.out.println("Which card would you like to remove?");
			System.out.println(myCards.toString());
			int cardChoice = input.nextInt();
			cardChoice = validate(1, myCards.getNumberOfCards(), cardChoice);
			myCards.removeCard(cardChoice);
		}
		
	}
	
	private void addCard() {
		CreditCardType type;
		String cardNumber, issueCompany;
		LocalDate issueDate, expirationDate;
		CreditCardStatus status;
		double creditLimit, currentBalance;
		
		System.out.println("Okay. Let's start with your card type. Please choose a type from the list ");
		for(int i = 0; i< CreditCardType.values().length; i++) {
			System.out.println((i+1) + ". " + CreditCardType.values()[i]);
		}
		int cardTypeChoice = input.nextInt();
		cardTypeChoice = validate(1, 3, cardTypeChoice);
		type = CreditCardType.values()[cardTypeChoice-1];
		
		System.out.println("What is the card number?");
		input.nextLine();
		cardNumber = input.nextLine();
		while((type == CreditCardType.AMEX && cardNumber.length() != 15) 
				|| (type != CreditCardType.AMEX && cardNumber.length() != 16)) {
			System.out.println("Amex cards should be 15 digits long. All other cards, 16.");
			cardNumber = input.nextLine();
		}
		
		System.out.println("When was the card issued? Please enter as yyyy-mm-dd");
		String userDate = input.nextLine();
		issueDate = validateDate(userDate, LocalDate.now().plusYears(-10));
		expirationDate = issueDate.plusYears(3); //The average credit card lasts three years
		
		System.out.println("Which company issued the card?");
		issueCompany = input.nextLine();
		
		if(expirationDate.isBefore(LocalDate.now())) {
			status = CreditCardStatus.EXPIRED;
		}
		else {
			System.out.println("What is the status of this card?");
			for(int i = 0; i< CreditCardStatus.values().length-1; i++) {
				System.out.println((i+1) + ". " + CreditCardStatus.values()[i]);
			}
			
			int cardStatusChoice = input.nextInt();
			cardStatusChoice = validate(1, 3, cardStatusChoice);
			status = CreditCardStatus.values()[cardStatusChoice-1];
		}
		System.out.println("What is the credit limit for this card?");
		creditLimit = input.nextDouble();
		creditLimit = validateDouble(100.0, 500000.0, creditLimit); //lowest credit limit is $100, highest credit limit is $500,000
		
		System.out.println("What is the current balance on this card?");
		currentBalance = input.nextDouble();
		currentBalance = validateDouble(-50.0, creditLimit, currentBalance);
		
		myCards.addCard(cardNumber, issueDate, expirationDate, issueCompany, type, status, creditLimit, currentBalance);
	}
	//int index, double transactionAmt, LocalDate transactionDate, PurchaseType purchaseType, Vendor vendor
	private void addPurchase(int cardChoice) {
		double amount;
		LocalDate date; 
		PurchaseType purchaseType;
		Vendor vendor;
		if(myCards.getCardStatus(cardChoice) == CreditCardStatus.CANCELLED) {
			System.out.println("We're sorry! Purchases cannot be added to cancelled credit cards.");		
		} else {
			System.out.println("What is the transaction amount?");
			amount = input.nextDouble();
	        double availCredit = myCards.getCardAvailCredit(cardChoice);
			amount = validateDouble(3.00, availCredit, amount);
			input.nextLine();
			
			System.out.println("\nPlease enter purchase date: yyyy-mm-dd");
			String userDate = input.nextLine();
			date = validateDate(userDate, myCards.getIssueDate(cardChoice)); //can only enter purchase within 5 years
			if(myCards.getCardStatus(cardChoice) == CreditCardStatus.EXPIRED) {
				date = validateExpired(date, cardChoice);
			}
			
			System.out.println("What is the purchase type?");
			for(int i = 0; i< PurchaseType.values().length; i++) {
				System.out.println((i+1) + ". " + PurchaseType.values()[i]);
			}
			int purchaseChoice = input.nextInt();
			purchaseChoice = validate(1, 8, purchaseChoice);
			purchaseType = PurchaseType.values()[purchaseChoice-1];
			input.nextLine();
			
			vendor = getVendor();
				try {
					myCards.addPurchase(cardChoice,amount, date, purchaseType, vendor);
				} catch (OutOfCreditException e) {
					e.getMessage();
				}
			
		}
	}
	
	private Vendor getVendor() {
		States stateType;
		System.out.println("What is the vendor name?");
		String name = input.nextLine();
		
		System.out.println("What is the street?");
		String street = input.nextLine();
		
		System.out.println("What is the city?");
		String city = input.nextLine();
		
		System.out.println("What is the state?");
		
		for(int i = 0; i< States.values().length; i++) {
			if(i % 10 == 0) {
				System.out.println();
			}
			System.out.print((i+1) + ". " + States.values()[i] + " ");
			
		}
		int stateChoice = input.nextInt();
		stateChoice = validate(1, 50, stateChoice);
		stateType = States.values()[stateChoice-1];
		input.nextLine();
		
		System.out.println("What is the zip?");
		String zip = input.nextLine();
		while(zip.length() != 5) {
			System.out.println("Zip must be 5 digits. Please enter a new zip");
			zip = input.nextLine();
		}
		
		return new Vendor(name, street, city, stateType, zip);
	}
	
	private void addPayment(int cardChoice) {
		PaymentType paymentType;
		BankAccount account;
		Double transAmount;
		LocalDate transactionDate;
		//payments can be made on any card type, including cancelled, since the user still may owe money
		//int index, PaymentType paymentType, BankAccount account, double transactionAmt, LocalDate transactionDate
		System.out.println("What is the payment type?");
		for(int i = 0; i< PaymentType.values().length; i++) {
			System.out.println((i+1) + ". " + PaymentType.values()[i]);
		}
		int paymentChoice = input.nextInt();
		paymentChoice = validate(1, 2, paymentChoice);
		paymentType = PaymentType.values()[paymentChoice-1];
		input.nextLine();
		
		account = createAccount();
		System.out.println("What is the payment amount?");
		transAmount = input.nextDouble();
		transAmount = validateDouble(3.00, myCards.getCardBalance(cardChoice), transAmount);
		input.nextLine();
		
		System.out.println("\nPlease enter payment date: yyyy-mm-dd");
		String userDate = input.nextLine();
	    transactionDate = validateDate(userDate, myCards.getIssueDate(cardChoice)); 
	    
	    myCards.addPayment(cardChoice, paymentType, account, transAmount, transactionDate);
		
		
	}
	
	private BankAccount createAccount() {
		System.out.println("What is the bank name?");
		String bankName = input.nextLine();
		
		System.out.println("What is the bank account number?");
		String accountNumber = input.nextLine();
		while(accountNumber.length() < 10 || accountNumber.length() > 12) {
			System.out.println("Account Number must be 10-12 digits");
			accountNumber = input.nextLine();
		}
		
		return new BankAccount(bankName, accountNumber);
		
	}
	private void addFee(int cardChoice) {
		if(myCards.getCardStatus(cardChoice) == CreditCardStatus.CANCELLED) {
			System.out.println("We're sorry! Fees cannot be added to cancelled credit cards.");
		} else {
			double amount;
			LocalDate date;
			FeeType type = FeeType.INTEREST; //Will definitely be changed;
			
			System.out.print("Please enter fee amount: ");
			amount = input.nextDouble();
			amount = validateDouble(0.0, 1000000, amount);
			input.nextLine();
			
			System.out.println("\nPlease enter fee date: yyyy-mm-dd");
			String userDate = input.nextLine();
			date = validateDate(userDate, myCards.getIssueDate(cardChoice));
			
			System.out.println("1. Late Payment \n2. Interest");
			System.out.print("\nPlease the number corresponding to the fee type: ");
			int choice = input.nextInt();
			choice = validate(1, 2, choice);
			
			switch(choice) {
			case 1: 
				type = FeeType.LATE_PAYMENT;
				break;
			case 2:
				type = FeeType.INTEREST;
				break;
			}		
			myCards.addFee(cardChoice, amount, date, TransactionType.FEE, type);
			
		}
	}
}
