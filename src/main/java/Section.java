
public class Section {
	private TA teacher;
	private String room;
	private String section;
	private int day;
	private int block;
	private String course;
	
	@Override
	public String toString() { 
		if (teacher == null)
			return "UNFILLED";
	      return teacher.getFirstName();
		//return section + " " + course + " " + day + " " + block;
	}

	public TA getTeacher() {
		return teacher;
	}

	public void setTeacher(TA teacher) {
		this.teacher = teacher;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getBlock() {
		return block;
	}

	public void setBlock(int block) {
		this.block = block;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	} 
}
