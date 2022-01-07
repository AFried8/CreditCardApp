import java.time.LocalDate;
import java.util.ArrayList;

public class CreditCards {

	ArrayList<CreditCard> cards;
	
	public CreditCards() {
		cards = new ArrayList<CreditCard>();
	}
	
	public ArrayList<String> activeCards() {
		ArrayList<String> activeCards = new ArrayList<>();
		for(CreditCard card: cards) {
			if(card.getCardStatus() == CreditCardStatus.ACTIVE) {
				activeCards.add(card.getCardName());
			}
		}
		return activeCards;
	}
	
	public double totalBalance() {
		double balance = 0;
		for(CreditCard card: cards) {
			balance += card.getCurrentBalance();
		}
		return balance;
	}
	
	public double totalAvailCredit() {
		double availCredit = 0;
		for(CreditCard card: cards) {
			availCredit += card.getAvailCredit();
		}
		return availCredit;
	}
	
	public boolean addCard(String cardNumber, LocalDate issueDate, LocalDate expirationDate, String issueCompany,
						CreditCardType cardType, CreditCardStatus cardStatus, double cardLimit,
						double currentBalance, double availCredit) {
		CreditCard newCard = new CreditCard(cardNumber, issueDate, expirationDate, issueCompany, cardType, cardStatus, cardLimit, currentBalance, availCredit);
		if(cards.contains(newCard)) {
			return false;
		}
		else {
			cards.add(newCard);
			return true;
		}
	}
	
	public void removeCard(int index) {
		cards.remove(index-1);
	}
	
	public void addPurchase(int index, double transactionAmt, LocalDate transactionDate, PurchaseType purchaseType, Vendor vendor) throws OutOfCreditException {
		cards.get(index-1).addPurchase(transactionAmt, transactionDate, purchaseType, vendor);
	}
	
	public void addFee(int index, double transactionAmt, LocalDate transactionDate,TransactionType transactionType, FeeType feeType) {
		cards.get(index-1).addFee(transactionAmt, transactionDate, transactionType, feeType);
	}
	
	public void addPayment(int index, PaymentType paymentType, BankAccount account, double transactionAmt, LocalDate transactionDate) {
		cards.get(index-1).addPayment(paymentType, account, transactionAmt, transactionDate);
	}	
	
	public double getCardBalance(int index) {
		return cards.get(index-1).getCurrentBalance();
	}
	public double getCardAvailCredit(int index) {
		return cards.get(index-1).getAvailCredit();
	}
	public double getCardCreditLimit(int index) {
		return cards.get(index-1).getCreditLimit();
	}
	
	public String largestPurchase() {
		double largestPurchase = 0.0;
		String largestPurchaseName = "";
		for(CreditCard card: cards) {
			Double cardsLargestPurchase = Double.parseDouble(card.largestPurchase().split("##")[0]);
			if(cardsLargestPurchase > largestPurchase) {
				largestPurchase = cardsLargestPurchase;
				largestPurchaseName = card.largestPurchase().split("##")[1];
			}
		}
		return largestPurchaseName;
	}
	
	public PurchaseType biggestSpendingArea() {
		//Doesn't deal with if two categories have same amount spent. will just return the first occurrence
		//If no purchases have been made on any cards, will return first PurchaseType enum
		double[] overallSpendingReview = new double[PurchaseType.values().length];
		for(CreditCard card: cards) {
			double[] spendingReview = card.spendingReview();
			for(int i = 0; i<spendingReview.length; i++) {
				overallSpendingReview[i] += spendingReview[i];
			}
		}
		
		double mostMoneySpent = 0;
		int mostMoneySpentCategory = 0;
		for(int j = 0; j<overallSpendingReview.length; j++) {
			if(overallSpendingReview[j] > mostMoneySpent) {
				mostMoneySpent = overallSpendingReview[j];
				mostMoneySpentCategory = j;
			}
		}
		
		return PurchaseType.values()[mostMoneySpentCategory];
	}
	
	//did we really want that method to return a purchase...?
	public Purchase getRecentPurchase(int index) {
		return cards.get(index-1).getMostRecentPurchase();
	}
	public Payment getRecentPayment(int index) {
		return cards.get(index-1).getMostRecentPayment();
	}
	
	
	
	
	
	
	
}
