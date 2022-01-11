
public class OutOfCreditException extends Exception{
	
	public OutOfCreditException() {
		super("Out of credit!!");
	}
	
	public OutOfCreditException(String message) {
		super(message);
	}

}
