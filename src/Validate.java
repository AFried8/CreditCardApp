import java.time.LocalDate;
import java.util.Scanner;

public class Validate {
	
	static Scanner input = new Scanner(System.in);
	
	public static int getCheckInt(int min, int max) {
		int userInput = input.nextInt();
		while(userInput < min || userInput > max) {
			if(min == max) {
				System.out.println("You can only enter option " + min);		
			}
			else {
				System.out.println("Please enter numbers " + min + "-" + max + " as they correspond to the options");
			}
			userInput = input.nextInt();
		}
		return userInput;
	}
	
	public static double checkDouble(double min, double max, double userInput) {
		while(userInput < min || userInput > max) {
			System.out.println("Please enter a number that is at least " + min + " and no more than " + max);
			userInput = input.nextDouble();
		}
		return userInput;
	}
	
	public static LocalDate checkDate(String userInput, LocalDate notBefore) {
		LocalDate date = LocalDate.now(); //this date will be changed
		boolean invalidFormat = true;
		while(invalidFormat) {
			try {
			date = LocalDate.parse(userInput);
			invalidFormat = false;
			}
			catch(Exception e) {
				System.out.println("That is not valid as a date. Please enter the date as yyyy-mm-dd");
				userInput = input.nextLine();
			}
		}
		
		while(date.isAfter(LocalDate.now()) || date.isBefore(notBefore)){
			System.out.println("This date is invalid. Please enter a new one");
			userInput = input.nextLine();
			date = checkDate(userInput, notBefore);
		}
		return date;
	}

}
