
public class Section {
	TA teacher;
	String room;
	String section;
	int day;
	int block;
	String course;
	
	@Override
	public String toString() { 
		if (teacher == null)
			return "UNFILLED";
	      return teacher.firstName;
		//return section + " " + course + " " + day + " " + block;
	} 
}
