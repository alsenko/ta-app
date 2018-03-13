import java.util.ArrayList;

public class Course {
	private String className;
	private ArrayList<Section> sections = new ArrayList<Section>();
	
	@Override
	public String toString() { 
	    return className;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public ArrayList<Section> getSections() {
		return sections;
	}

	public void setSections(ArrayList<Section> sections) {
		this.sections = sections;
	} 

}
