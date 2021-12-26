import java.time.LocalDate;

public class Fee extends Transaction{
	
	private FeeType feeType;

	public FeeType getFeeType() {
		return feeType;
	}
	
	public Fee(long lastTransactionID, double transactionAmt, LocalDate transactionDate,
			TransactionType transactionType, FeeType feeType) {
		super(lastTransactionID, transactionAmt, transactionDate, TransactionType.FEE);
		this.feeType = feeType;
	}

	public void setFeeType(FeeType feeType) {
		this.feeType = feeType;
	}

	
}
