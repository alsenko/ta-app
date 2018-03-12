import java.util.ArrayList;

public class TA {
	String firstName;
	String lastName;
	Day monday;
	Day tuesday;
	Day wednesday;
	Day thursday;
	Day friday;
	int status;
	boolean fullTA;
	String pref1;
	String pref2;
	String pref3;
	ArrayList<Section> teaching = new ArrayList<Section>();
	
	
	
	
	
	public Day getDay(int day) {
		switch (day) {
		case 1: return this.monday;
			
		case 2: return this.tuesday;
			
		case 3: return this.wednesday;
			
		case 4: return this.thursday;
			
		case 5: return this.friday;
		}
		
		return null;
	}
	
	@Override
	public String toString() { 
		String full = (fullTA) ? "FULL TA" : "HALF TA";
	    return firstName + " " + lastName + " " + monday.toString() + " " 
	+ tuesday.toString() + " " + wednesday.toString() + " " + thursday.toString() + " " + friday.toString() 
	+ " " + status + " " + full + " " + pref1+ " " + pref2 + " " + pref3 ;
	} 	
}
