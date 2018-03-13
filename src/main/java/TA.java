import java.util.ArrayList;

public class TA {
	private String firstName;
	private String lastName;
	private Day monday;
	private Day tuesday;
	private Day wednesday;
	private Day thursday;
	private Day friday;
	private int status;
	private boolean fullTA;
	private String pref1;
	private String pref2;
	private String pref3;
	private ArrayList<Section> teaching = new ArrayList<Section>();
	
	
	
	
	
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Day getMonday() {
		return monday;
	}

	public void setMonday(Day monday) {
		this.monday = monday;
	}

	public Day getTuesday() {
		return tuesday;
	}

	public void setTuesday(Day tuesday) {
		this.tuesday = tuesday;
	}

	public Day getWednesday() {
		return wednesday;
	}

	public void setWednesday(Day wednesday) {
		this.wednesday = wednesday;
	}

	public Day getThursday() {
		return thursday;
	}

	public void setThursday(Day thursday) {
		this.thursday = thursday;
	}

	public Day getFriday() {
		return friday;
	}

	public void setFriday(Day friday) {
		this.friday = friday;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isFullTA() {
		return fullTA;
	}

	public void setFullTA(boolean fullTA) {
		this.fullTA = fullTA;
	}

	public String getPref1() {
		return pref1;
	}

	public void setPref1(String pref1) {
		this.pref1 = pref1;
	}

	public String getPref2() {
		return pref2;
	}

	public void setPref2(String pref2) {
		this.pref2 = pref2;
	}

	public String getPref3() {
		return pref3;
	}

	public void setPref3(String pref3) {
		this.pref3 = pref3;
	}

	public ArrayList<Section> getTeaching() {
		return teaching;
	}

	public void setTeaching(ArrayList<Section> teaching) {
		this.teaching = teaching;
	} 	
}
