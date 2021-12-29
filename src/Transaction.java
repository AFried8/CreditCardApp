import java.time.LocalDate;

public class Transaction {
	
	private long transactionID;
	private long lastTransactionID;
	private double transactionAmt;
	private LocalDate transactionDate;
	private TransactionType transactionType;
	
	public Transaction(long lastTransactionID, double transactionAmt, LocalDate transactionDate,
			TransactionType transactionType) {
		this.transactionID = lastTransactionID + 1;
		this.lastTransactionID = lastTransactionID;
		this.transactionAmt = transactionAmt;
		this.transactionDate = transactionDate;
		this.transactionType = transactionType;
	}
	
	public long getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(long transactionID) {
		this.transactionID = transactionID;
	}

	public long getLastTransactionID() {
		return lastTransactionID;
	}

	public void setLastTransactionID(long lastTransactionID) {
		this.lastTransactionID = lastTransactionID;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	
	public double getTransactionAmt() {
		return transactionAmt;
	}
	

}
