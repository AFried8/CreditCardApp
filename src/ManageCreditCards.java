import java.util.Scanner;

public class ManageCreditCards {
	
	Scanner input = new Scanner(System.in);
	CreditCards myCards;
	Menu mainMenu;
	Menu manageCards;
	Menu financialStats;
	Menu manageCard;
	
	public ManageCreditCards() {
		myCards = new CreditCards();
		createMenus();
	}
	
	private void createMenus() {
		mainMenu = new Menu("Main Menu");
		mainMenu.addOption("Manage my wallet");
		mainMenu.addOption("See my financial stats");
		mainMenu.addOption("Manage specific card");
		mainMenu.addOption("Exit");
		
		manageCards = new Menu("Manage my wallet");
		manageCards.addOption("Add a new credit card");
		manageCards.addOption("Remove a credit card");
		
		financialStats = new Menu("Financial Stats");
		financialStats.addOption("See my total balance");
		financialStats.addOption("See my total available credit");
		financialStats.addOption("See my largest purchase");
		financialStats.addOption("See my biggest spending area");
		
		manageCard = new Menu("Manage Card");
		manageCard.addOption("See my balance");
		manageCard.addOption("See my credit limit");
		manageCard.addOption("See my available credit");
		manageCard.addOption("Add a purchase");
		manageCard.addOption("Add a fee");
		manageCard.addOption("See my most recent purchase");
		manageCard.addOption("See my most recent payment");
	}
	
	public void mainMenu() {
		System.out.println(mainMenu);
		int choice = input.nextInt();
		choice = validate(1, mainMenu.getMax(), choice);
		switch (choice) {
		case 1:
			manageCards();
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
	
	public void manageCards() {
		System.out.println(manageCards);
		int choice = input.nextInt();
		choice = validate(1, manageCards.getMax(), choice);
		switch (choice) {
		case 1:
			addCard();
			break;
		case 2:
			removeCard();
		}
	}
	
	public void financialStats() {
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
		}
	}
	
	public void manageCard() {
		System.out.println("Which card would you like to manage?");
		System.out.println(myCards.toString());		//do we have a to string??
		int cardChoice = input.nextInt();
		cardChoice = validate(1, myCards.getMax(), cardChoice);
		
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
			addPurchase(cardChoice);
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
		}
	}
	

	public int validate(int min, int max, int userInput) {
		
		while(userInput < min || userInput > max) {
			System.out.println("Please enter numbers " + min + "-" + max + " as they correspond to the options");
			userInput = input.nextInt();
		}
		return userInput;
	}
	
	public void removeCard() {
		System.out.println("Which card would you like to remove?");
		System.out.println(myCards.toString());		//do we have a to string??
		int cardChoice = input.nextInt();
		cardChoice = validate(1, myCards.getMax(), cardChoice);
		
		myCards.removeCard(cardChoice);
		
	}
}
