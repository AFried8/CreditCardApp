import java.time.LocalDate;

public class Purchase extends Transaction{

	private PurchaseType purchaseType;
	private Vendor vendor;
	private String name;
	
	public Purchase(long lastTransactionID, double transactionAmt, LocalDate transactionDate, 
			PurchaseType purchaseType, Vendor vendor) {
		super(lastTransactionID, transactionAmt, transactionDate, TransactionType.PURCHASE);
		this.purchaseType = purchaseType;
		this.vendor = vendor;
		name = (vendor.getName() + ": " + transactionAmt);
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
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(vendor.getName());
		str.append(": " + getTransactionAmt());
		
		return str.toString();
	}
}
