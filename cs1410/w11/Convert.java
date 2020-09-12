import java.util.Scanner;

/**
 * This program asks the user for a unit and a value and converts the value
 * to another unit.
 * 
 * ============
 * 
 * Qianlang Chen
 * u1172983
 * H 11/08/18
 */
public class Convert {
    
    /** Application entry point. **/
    static public void main(String[] args) {
	// Ask the user for the unit combo to convert.
	
	Scanner input = new Scanner(System.in);
	
	System.out.println("Hi! Welcome to Asianboii's converter!\n" +
			   "Which unit combination do you want me to" +
			   "convert? (Enter 1 or 2)\n" +
			   "1: Feet to Meters\n" + 
			   "2: Pound to Grams");
	
	int option = input.nextInt();
	
	// Figure out which unit combo the user has chosen.
	
	String convertFrom, convertFromPlural, convertTo, convertToPlural;
	double ratio;
	
	if (option == 1) {
	    convertFrom = "foot";
	    convertFromPlural = "feet";
	    convertTo = "meter";
	    convertToPlural = "meters";
	    
	    ratio = 0.3048;
	} else if (option == 2) {
	    convertFrom = "pound";
	    convertFromPlural = "pounds";
	    convertTo = "gram";
	    convertToPlural = "grams";
	    
	    ratio = 453.59237;
	} else {
	    System.out.println("Invalid converting option!!");
	    return;
	}
	
	// Ask the user for the value to convert and convert it.
	
	System.out.println("How many " + convertFromPlural + " do you " +
			   "want me to convert to " + convertToPlural +
			   "?");
	
	double value = input.nextDouble();
	double convertedValue = value * ratio;
	
	// Output the results.
	
	System.out.println(value + " " +
			   pluralize(value, convertFrom, convertFromPlural) +
			   " is " + convertedValue + " " +
			   pluralize(convertedValue, convertTo,
				     convertToPlural) +
			   "!");
    }
    
    static private String pluralize(double count, String word, String plural) {
	return count == 1 ? word : plural;
    }
    
}
