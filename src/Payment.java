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
	
}
