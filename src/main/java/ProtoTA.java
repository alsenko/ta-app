
public class ProtoTA {
	private String firstName;
	private String lastName;
	private String taType;
	private String monday;
	private String tuesday;
	private String wednesday;
	private String thursday;
	private String friday;
	private String pref1;
	private String pref2;
	private String pref3;
	
	
	
	@Override
	public String toString() { 
	    return firstName + " | " + lastName + " | " + taType + " | " + monday + " | " + friday + " | " + pref2 ;
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



	public String getTaType() {
		return taType;
	}



	public void setTaType(String taType) {
		this.taType = taType;
	}



	public String getMonday() {
		return monday;
	}



	public void setMonday(String monday) {
		this.monday = monday;
	}



	public String getTuesday() {
		return tuesday;
	}



	public void setTuesday(String tuesday) {
		this.tuesday = tuesday;
	}



	public String getWednesday() {
		return wednesday;
	}



	public void setWednesday(String wednesday) {
		this.wednesday = wednesday;
	}



	public String getThursday() {
		return thursday;
	}



	public void setThursday(String thursday) {
		this.thursday = thursday;
	}



	public String getFriday() {
		return friday;
	}



	public void setFriday(String friday) {
		this.friday = friday;
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
	
	
}
