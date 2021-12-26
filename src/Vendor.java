
public class Vendor {
	private String name;
	private Address address;
	
	public Vendor(String name, String street, String city, States state, String zip) {
		this.name = name;
		this.address = new Address(street, city, state, zip);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		
		return address.toString();
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
}
