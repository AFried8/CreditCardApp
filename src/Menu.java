import java.util.ArrayList;

public class Menu {
	
	String name;
	ArrayList<String> options;
	
	public Menu(String name) {
		this.name = name;
		options = new ArrayList<String>();
	}
	
	public void addOption(String option) {
		options.add(option);
	}
	
	public int getMax() {
		return options.size()-1;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder(name);
		for(int i = 0; i<= options.size()-1; i++) {
			str.append("\n" + (i+1) + ". " + options.get(i));
		}
		return str.toString();
	}

}
