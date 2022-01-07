import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class CreditCard implements Comparable<CreditCard>{
	
	private String cardNumber;
	private String cardName;
	private LocalDate issueDate;
	private LocalDate expirationDate;
	//Not sure what issueCompany is
	private String issueCompany;
	private CreditCardType creditCardType;
	private CreditCardStatus cardStatus;
	private double creditCardLimit;
	private double currentBalance;
	private double availCredit;
	private long lastTransactionID = 0;
	//decide on data structure
	private ArrayList<Transaction> transactions;
	private ArrayList<Purchase> purchases;
	private ArrayList<Payment> payments;
	private ArrayList<Fee> fees;
	
	public CreditCard(String cardNumber, LocalDate issueDate, LocalDate expirationDate, String issueCompany,
						CreditCardType cardType, CreditCardStatus cardStatus, double cardLimit,
						double currentBalance, double availCredit) {
		this.cardNumber = cardNumber;
		this.cardName = (cardType + "-" + cardNumber.substring(cardNumber.length()-5));
		this.issueDate = issueDate;
		this.expirationDate = expirationDate;
		this.issueCompany = issueCompany;
		this.creditCardType = cardType;
		this.cardStatus = cardStatus;
		this.creditCardLimit = cardLimit;
		this.currentBalance = currentBalance;
		this.availCredit = availCredit;
		//transactions = new ArrayList<>();
		purchases = new ArrayList<>();
		payments = new ArrayList<>();
		fees = new ArrayList<>();
	}
	
	public String getCardName() {
		return cardName;
	}
	
	public void addPurchase(double transactionAmt, LocalDate transactionDate, PurchaseType purchaseType, Vendor vendor) throws OutOfCreditException{
		//questionable if the input validation belongs here
		if(transactionAmt > availCredit) {
			throw new OutOfCreditException();
		}
		Purchase thisPurchase = new Purchase(lastTransactionID, transactionAmt, transactionDate, purchaseType, vendor);
		purchases.add(thisPurchase);
		//Assuming all transactions are entered in the order they occurred;
		lastTransactionID++;
		currentBalance +=transactionAmt;
		availCredit -= transactionAmt;
	}
	
	public void addPayment(PaymentType paymentType, BankAccount account, double transactionAmt, LocalDate transactionDate) {
		Payment thisPayment = new Payment(paymentType, account, lastTransactionID, transactionAmt,transactionDate);
		payments.add(thisPayment);
		lastTransactionID++;
		currentBalance -= transactionAmt;
		availCredit +=transactionAmt;
	}
	
	public void addFee(double transactionAmt, LocalDate transactionDate,TransactionType transactionType, FeeType feeType) {		
		Fee thisFee = new Fee(lastTransactionID, transactionAmt, transactionDate, transactionType, feeType);
		fees.add(thisFee);
		lastTransactionID++;
		currentBalance += transactionAmt;
		availCredit -= transactionAmt;
	}
	
	public double getCurrentBalance() {
		return currentBalance;
	}
	
	public Purchase getLargestPurchase() {
		//Check in user CreditCards if purchases is empty
		double largestPurchaseAmt = 0;
		Purchase largestPurchase = purchases.get(0);
		for(Purchase p: purchases) {
			if(p.getTransactionAmt() > largestPurchaseAmt) {
				largestPurchaseAmt = p.getTransactionAmt();
				largestPurchase = p;
			}
		}
		return largestPurchase;
	}
	
	public double getAvailCredit() {
		return availCredit;
	}
	
	public double getCreditLimit() {
		return creditCardLimit;
	}
	
	public double getTotalfees() {
		double totalFees = 0;
		for(Fee f: fees) {
			totalFees += f.getTransactionAmt();
		}
		return totalFees;
	}
	
	public Purchase getMostRecentPurchase() {
		//Check in CreditCards if Purchases is empty
		LocalDate mostRecentPurchaseDate = LocalDate.parse("1900-01-01");
		Purchase mostRecentPurchase = purchases.get(0);
		for(Purchase p: purchases) {
			if(p.getTransactionDate().compareTo(mostRecentPurchaseDate)>0) {
				mostRecentPurchaseDate = p.getTransactionDate();
				mostRecentPurchase = p;
			}
		}
		return mostRecentPurchase;
	}
	
	public Payment getMostRecentPayment() {
		//Check in CreditCards if Purchases is empty
			LocalDate mostRecentPaymentDate = LocalDate.parse("1900-01-01");
			Payment mostRecentPayment = payments.get(0);
			for(Payment p: payments) {
				if(p.getTransactionDate().compareTo(mostRecentPaymentDate)>0) {
					mostRecentPaymentDate = p.getTransactionDate();
					mostRecentPayment = p;
				}
			}
			return mostRecentPayment;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(issueCompany + "-" + creditCardType);
		str.append("\n" + cardNumber);
		str.append("\n" + expirationDate);
		return str.toString();
	}
	
	public String status() {
		StringBuilder str = new StringBuilder(this.toString());
		str.append("\nBalance: " + currentBalance);
		str.append("\nAvailable Credit: " + availCredit);
		return str.toString();
	}

	public CreditCardStatus getCardStatus() {
		return cardStatus;
	}
	
	public HashMap<String, Double> largestPurchase(){
		HashMap<String, Double> purchase = new HashMap<>();
		double largestPurchase = 0;
		for(Purchase p: purchases) {
			if (p.getTransactionAmt() > largestPurchase) {
				largestPurchase = p.getTransactionAmt();
				purchase.put(p.getName(), p.getTransactionAmt());
				}
		}
		return purchase;
	}
	
	@Override
	public int compareTo(CreditCard other) {
		return cardNumber.compareTo(other.getCardNumber());
	}

	public String getCardNumber() {
		return cardNumber;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreditCard other = (CreditCard) obj;
		if (cardNumber == null) {
			if (other.cardNumber != null)
				return false;
		} else if (!cardNumber.equals(other.cardNumber))
			return false;
		return true;
	}
	
	


	/* private long getLastTransactionID() {
		long lastTransactionID;
		if(transactions.size()!=0) {
			lastTransactionID = transactions.get(transactions.size()-1).getTransactionID();
		}
		else {
			lastTransactionID = 0;
		}
		return lastTransactionID;
	}
	*/
	
}
