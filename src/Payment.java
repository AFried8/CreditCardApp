import java.time.LocalDate;

public class Payment extends Transaction{

	private PaymentType paymentType;
	private BankAccount account;
	
	public Payment(PaymentType paymentType, BankAccount account, long lastTransactionID, 
					double transactionAmt, LocalDate transactionDate) {
		super(lastTransactionID, transactionAmt, transactionDate, TransactionType.PAYMENT);
		this.paymentType = paymentType;
		this.account = account;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public BankAccount getAccount() {
		return account;
	}

	public void setAccount(BankAccount account) {
		this.account = account;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Payment Type: " + paymentType);
		str.append("\nAmount: " + getTransactionAmt());
		return str.toString();
	}
}
