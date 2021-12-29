import java.time.LocalDate;

public class Purchase extends Transaction{

	private PurchaseType purchaseType;
	private Vendor vendor;
	
	public Purchase(long lastTransactionID, double transactionAmt, LocalDate transactionDate, 
			PurchaseType purchaseType, Vendor vendor) {
		super(lastTransactionID, transactionAmt, transactionDate, TransactionType.PURCHASE);
		this.purchaseType = purchaseType;
		this.vendor = vendor;
	}

	public PurchaseType getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(PurchaseType purchaseType) {
		this.purchaseType = purchaseType;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	
	
}