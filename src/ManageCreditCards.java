//Need to change do-while loops to regular while loops because options need to be printed again after the user chooses
//Can't just call a the main menu method... won't exit properly
//Need to figure out how to clear the buffer in the date validation


import java.time.LocalDate;
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
		manageWallet.addOption("Return to main menu");
		
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
		manageCard.addOption("Return to card list");
	}
	
	public void mainMenu() {
		int choice; 
		do {
			System.out.println(mainMenu);
			choice = input.nextInt();
			choice = Validate.checkInt(1, mainMenu.numberOfOptions(), choice);
			switch (choice) {
			case 1:
				manageWallet();
				break;
			case 2: 
				financialStats();
				break;
			case 3:
				availableCards();
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
			choice = Validate.checkInt(1, manageWallet.numberOfOptions(), choice);
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
				choice = Validate.checkInt(1, financialStats.numberOfOptions(), choice);
				
				switch (choice) {
				case 1: 
					System.out.println("The total balance of all your cards is $" + myCards.totalBalance());
					break;
				case 2:
					System.out.println("Your total available credit is $" + myCards.totalAvailCredit());
					break;
				case 3:
					if(myCards.hasPurchases()) {
						System.out.println("Your largest purchase was " + myCards.largestPurchase());
					} else {
						System.out.println("You have not made any purchases yet");
					}
					break;
				case 4:
					if(myCards.hasPurchases()) {
						System.out.println("Your biggest spending area is " + myCards.biggestSpendingArea());
					} else {
						System.out.println("You have not made any purchases yet");
					}
					break;
				}
			} while(choice < 5);
		}
	}
	
	public void availableCards() {
		if(myCards.getNumberOfCards() < 1) {
			System.out.println("Sorry you don't have any cards to manage since you didn't register any cards. ");
		}
		else {
			System.out.println("Which card would you like to manage?");
			System.out.println(myCards.toString());
			System.out.println(myCards.getNumberOfCards() + 1 + ". Return to main menu");
			int cardChoice = input.nextInt();
			cardChoice = Validate.checkInt(1, myCards.getNumberOfCards()+1, cardChoice);
			if(cardChoice == myCards.getNumberOfCards()+1) {
				mainMenu();
			}
			else {
				manageCard(cardChoice);
			}
		}
	}
	
	public void manageCard(int cardChoice) {
		
		System.out.println(manageCard);
		int choice;
		do {
			choice = input.nextInt(); 
			choice = Validate.checkInt(1, manageCard.numberOfOptions(), choice);
			
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
					System.out.println("Most recent purchase was " + myCards.getRecentPurchase(cardChoice));
				} else {
					System.out.println("No purchases have been made on this card");
				}
				break;
			case 8:
				if(myCards.hasPayment(cardChoice)) {
					System.out.println("Most recent payment is \n" + myCards.getRecentPayment(cardChoice));
				} else {
					System.out.println("No payments have been made on this card");
				}
				break;
			case 9:
				availableCards();
			}
		}
		while(choice < 10);
		
	}
	
	
	public LocalDate validateExpired(LocalDate date, int cardChoice) {
		while(date.isAfter(myCards.getExpirationDate(cardChoice))){
			System.out.println("Transaction date must be before expiration date. Please enter a new one");
			String userDate = input.nextLine();
			date = Validate.checkDate(userDate, myCards.getIssueDate(cardChoice) );
		} return date;
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
		cardTypeChoice = Validate.checkInt(1, CreditCardType.values().length, cardTypeChoice);
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
		issueDate = Validate.checkDate(userDate, LocalDate.now().plusYears(-10));
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
			cardStatusChoice = Validate.checkInt(1, 3, cardStatusChoice);
			status = CreditCardStatus.values()[cardStatusChoice-1];
		}
		
		System.out.println("What is the credit limit for this card?");
		creditLimit = input.nextDouble();
		creditLimit = Validate.checkDouble(100.0, 500000.0, creditLimit, "Credit limit can be at minimum $100, and at maximum, $500,000");
		
		System.out.println("What is the current balance on this card?");
		currentBalance = input.nextDouble();
		currentBalance = Validate.checkDouble(-50.0, creditLimit, currentBalance, "Your balance can't be less than -50 or greater than your credit limit, " + creditLimit);
		
		myCards.addCard(cardNumber, issueDate, expirationDate, issueCompany, type, status, creditLimit, currentBalance);
		System.out.println("Card added \n");
	}
	
	public void removeCard() {
		if(myCards.getNumberOfCards() < 1) {
			System.out.println("Sorry, there are no cards registered.\n");
		}
		else {
			System.out.println("Which card would you like to remove?");
			System.out.println(myCards.toString() + (myCards.getNumberOfCards()+1) + ".Return to previous menu");
			int cardChoice = input.nextInt();
			cardChoice = Validate.checkInt(1, myCards.getNumberOfCards()+1, cardChoice);
			if(cardChoice == myCards.getNumberOfCards()+1) {
				mainMenu();
			}
			else {
				myCards.removeCard(cardChoice);
				System.out.println("Card removed \n");
				System.out.println(manageWallet);
			}
		}		
	}
	
	private void addPurchase(int cardChoice) {
		double amount;
		LocalDate date; 
		PurchaseType purchaseType;
		Vendor vendor;
		
		if(myCards.getCardStatus(cardChoice) == CreditCardStatus.CANCELLED) {
			System.out.println("We're sorry! Purchases cannot be added to cancelled credit cards.");		
		} else {
			System.out.println("What is the purchase amount?");
			amount = input.nextDouble();
			amount = Validate.checkDouble(0.00, myCards.getCardAvailCredit(cardChoice), amount, 
											"You cannot make a purchase that's less than 0 or greater than your available credit, $" + myCards.getCardAvailCredit(cardChoice));
			input.nextLine(); //to clear the buffer
			
			System.out.println("\nPlease enter purchase date: yyyy-mm-dd");
			String userDate = input.nextLine();
			date = Validate.checkDate(userDate, myCards.getIssueDate(cardChoice)); //can only enter purchase within 5 years
			if(myCards.getCardStatus(cardChoice) == CreditCardStatus.EXPIRED) {
				date = validateExpired(date, cardChoice);
			}
			
			System.out.println("What is the purchase type?");
			for(int i = 0; i< PurchaseType.values().length; i++) {
				System.out.println((i+1) + ". " + PurchaseType.values()[i]);
			}
			int purchaseChoice = input.nextInt();
			purchaseChoice = Validate.checkInt(1, 8, purchaseChoice);
			purchaseType = PurchaseType.values()[purchaseChoice-1];
			
			vendor = getVendor();
				try {
					myCards.addPurchase(cardChoice,amount, date, purchaseType, vendor);
					System.out.println("Purchase added \n");
				} catch (OutOfCreditException e) {
					e.getMessage();
				}
			
		}
		System.out.println(manageCard);
		
	}
	
	private Vendor getVendor() {
		States stateType;
		System.out.println("What is the vendor name?");
		String name = input.nextLine();
		
		System.out.println("What is the vendor's street address?");
		String street = input.nextLine();
		
		System.out.println("What is the vendor's city?");
		String city = input.nextLine();
		
		System.out.println("What is the vendor's state?");
		
		for(int i = 0; i< States.values().length; i++) {
			if(i % 10 == 0) {
				System.out.println();
			}
			System.out.print((i+1) + ". " + States.values()[i] + " ");			
		}
		System.out.println();
		int stateChoice = input.nextInt();
		stateChoice = Validate.checkInt(1, 50, stateChoice);
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
		paymentChoice = Validate.checkInt(1, 2, paymentChoice);
		paymentType = PaymentType.values()[paymentChoice-1];
		input.nextLine();
		
		account = createAccount();
		System.out.println("How much do you wish to pay?");
		transAmount = input.nextDouble();
		transAmount = Validate.checkDouble(3.00, myCards.getCardBalance(cardChoice), transAmount);
		input.nextLine();
		
		System.out.println("\nPlease enter payment date: yyyy-mm-dd");
		String userDate = input.nextLine();
	    transactionDate = Validate.checkDate(userDate, myCards.getIssueDate(cardChoice)); 
	    
	    myCards.addPayment(cardChoice, paymentType, account, transAmount, transactionDate);
		System.out.println("Payment added");
		System.out.println(manageCard);
		
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
			amount = Validate.checkDouble(0.0, 1000000, amount);
			input.nextLine();
			
			System.out.println("\nPlease enter fee date: yyyy-mm-dd");
			String userDate = input.nextLine();
			date = Validate.checkDate(userDate, myCards.getIssueDate(cardChoice));
			
			System.out.println("1. Late Payment \n2. Interest");
			System.out.print("\nPlease the number corresponding to the fee type: ");
			int choice = input.nextInt();
			choice = Validate.checkInt(1, 2, choice);
			
			switch(choice) {
			case 1: 
				type = FeeType.LATE_PAYMENT;
				break;
			case 2:
				type = FeeType.INTEREST;
				break;
			}		
			myCards.addFee(cardChoice, amount, date, TransactionType.FEE, type);
			System.out.println("Fee Added");
			System.out.println(manageCard);
			
		}
	}
}
