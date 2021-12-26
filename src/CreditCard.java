import java.time.LocalDate;

public class CreditCard {
	
	private String creditCardID;
	private LocalDate issueDate;
	private LocalDate expirationDate;
	//Not sure what issueCompany is
	private String issueCompany;
	private CreditCardType creditCardType;
	private CreditCardStatus creditCardStatus;
	private double creditCardLimit;
	private double currentBalance;
	private double availCredit;
	//decide on data structure
	private Transaction[] transactions;
	
	public CreditCard(String creditCardID, LocalDate issueDate, LocalDate expirationDate, String issueCompany,
						CreditCardType creditCardType, CreditCardStatus creditCardStatus, double creditCardLimit,
						double currentBalance, double availCredit) {
		this.creditCardID = creditCardID;
		this.issueDate = issueDate;
		this.expirationDate = expirationDate;
		this.issueCompany = issueCompany;
		this.creditCardType = creditCardType;
		this.creditCardStatus = creditCardStatus;
		this.creditCardLimit = creditCardLimit;
		this.currentBalance = currentBalance;
		this.availCredit = availCredit;
	}
	
	public void addPurchase() {
		
	}
	
	public void addPayment() {
		
	}
	
	public void addFee() {
		
	}
	
	public double getCurrentBalance() {
		return currentBalance;
	}
	
	public Purchase getLargestPurchase() {
		
	}
	
	public double getAvailCredit() {
		return availCredit;
	}
	
	public double getTotalfees() {
		
	}
	
	public Purchase getMostRecentPurchase() {
	
	}
	
	public Payment getMostRecentPayment() {
		
	}

	
}
