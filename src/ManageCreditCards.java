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
		manageCard.addOption("Add a fee");
		manageCard.addOption("See my most recent purchase");
		manageCard.addOption("See my most recent payment");
		manageCard.addOption("Return to main menu");
	}
	
	public void mainMenu() {
		System.out.println(mainMenu);
		int choice = input.nextInt();
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
	}
	
	public void manageWallet() {
		System.out.println(manageWallet);
		int choice = input.nextInt();
		choice = validate(1, manageWallet.getMax(), choice);
		switch (choice) {
		case 1:
			addCard();
			break;
		case 2:
			removeCard();
			break;
		case 3:
			mainMenu();
		}
	}
	
	public void financialStats() {
		if(myCards.getNumberOfCards() < 1) {
			System.out.println("Sorry you don't have any financial stats since you didn't register any cards yet");
		}
		else {
			System.out.println(financialStats);
			int choice = input.nextInt();
			choice = validate(1, financialStats.getMax(), choice);
			switch (choice) {
			case 1: 
				System.out.println("The total balance of all your cards is " + myCards.totalBalance());
				break;
			case 2:
				System.out.println("Your total available credit is " + myCards.totalAvailCredit());
				break;
			case 3:
				System.out.println("Your largest purchase was " + myCards.largestPurchase());
				break;
			case 4:
				System.out.println("Your biggest spending area is " + myCards.biggestSpendingArea());
				break;
			case 5:
				mainMenu();
			}
			
		}
	}
	
	public void manageCard() {
		System.out.println("Which card would you like to manage?");
		System.out.println(myCards.toString());
		int cardChoice = input.nextInt();
		cardChoice = validate(1, myCards.getNumberOfCards(), cardChoice);
		
		System.out.println(manageCard);
		int choice = input.nextInt();
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
			//addPurchase(cardChoice);
			break;
		case 5:
			addFee(cardChoice);
			break;
		case 6:
			System.out.println("Most recent purchase is " + myCards.getRecentPurchase(cardChoice));
			break;
		case 7:
			System.out.println("Most recent payment is " + myCards.getRecentPayment(cardChoice));
			break;
		case 8:
			mainMenu();
		}
	}


	public int validate(int min, int max, int userInput) {
		
		while(userInput < min || userInput > max) {
			System.out.println("Please enter numbers " + min + "-" + max + " as they correspond to the options");
			userInput = input.nextInt();
		}
		return userInput;
	}
	
	private double validateDouble(double min, double max, double userInput) {
		while(userInput < min || userInput > max) {
			System.out.println("Please enter a number greater than " + min + " and less than " + max);
			userInput = input.nextDouble();
		}
		return userInput;
	}
	
	public LocalDate validateDate(String userInput, int maxYears) {
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
		
		while(date.isAfter(LocalDate.now()) || date.isBefore(LocalDate.now().plusYears(-maxYears))){
			System.out.println("This date is invalid. Please enter a new one");
			userInput = input.nextLine();
			date = validateDate(userInput, maxYears);
		}
		return date;
	}
	
	public void removeCard() {
		if(myCards.getNumberOfCards() < 1) {
			System.out.println("Sorry you didn't register any cards yet");
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
		System.out.println(type.toString());
		while((type == CreditCardType.AMEX && cardNumber.length() != 15) 
				|| (type != CreditCardType.AMEX && cardNumber.length() != 16)) {
			System.out.println("Amex cards should be 15 digits long. All other cards, 16.");
			cardNumber = input.nextLine();
		}
		
		System.out.println("When was the card issued? Please enter as yyyy-mm-dd");
		String userDate = input.nextLine();
		issueDate = validateDate(userDate, 10);
		expirationDate = issueDate.plusYears(3); //The average credit card lasts three years
		
		System.out.println("Which company issued the card?");
		issueCompany = input.nextLine();
		
		System.out.println("What is the status of this card?");
		for(int i = 0; i< CreditCardStatus.values().length; i++) {
			System.out.println((i+1) + ". " + CreditCardStatus.values()[i]);
		}
		int cardStatusChoice = input.nextInt();
		cardStatusChoice = validate(1, 4, cardStatusChoice);
		status = CreditCardStatus.values()[cardStatusChoice-1];
		
		System.out.println("What is the credit limit for this card?");
		creditLimit = input.nextDouble();
		creditLimit = validateDouble(0.0, 500000.0, creditLimit); //highest credit limit is $500,000
		
		System.out.println("What is the current balance on this card?");
		currentBalance = input.nextDouble();
		currentBalance = validateDouble(Double.MIN_VALUE, creditLimit, currentBalance);
		
		myCards.addCard(cardNumber, issueDate, expirationDate, issueCompany, type, status, creditLimit, currentBalance);
		System.out.println(myCards);
	}
	
	private void addFee(int cardChoice) {
		double amount;
		LocalDate date;
		FeeType type = FeeType.INTEREST; //Will definitely be changed;
		
		System.out.print("Please enter fee amount: ");
		amount = input.nextDouble();
		amount = validateDouble(0.0, Double.MAX_VALUE, amount);
		
		System.out.print("\nPlease enter fee date: yyyy-mm-dd");
		String userDate = input.nextLine();
		date = validateDate(userDate, 5);
		
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
