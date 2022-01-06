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
	
	public String largestPurchase() {
		int largestPurchase = 0;
		for(CreditCard card: cards) {
			if(card.largestPurchase().values(). > largestPurchase) {
				largestPurchase = card.largestPurchase();
			}
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
	
	
	
	
	
	
	
	
}
