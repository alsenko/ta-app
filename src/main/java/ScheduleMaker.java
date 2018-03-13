import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleMaker {
	 public static void printCurrentList(ArrayList<Course> schedule) {
		 for (Course x : schedule){
				System.out.println(x.toString());
				for (Section y : x.getSections()){
					System.out.println(y.getDay() + " " + y.getBlock() + " " + y.getTeacher());
				}
			}
		
	}

	public static HashMap<Integer,HashMap<Integer,ArrayList<TA>>> createMap(ArrayList<TA> taList){
		Collections.shuffle(taList);
		HashMap<Integer,HashMap<Integer,ArrayList<TA>>> taMap = new HashMap<Integer,HashMap<Integer,ArrayList<TA>>>();
		for (int i = 1; i <= 5; i++){
			HashMap<Integer, ArrayList<TA>> dayMap = new HashMap<Integer,ArrayList<TA>>();
			for (int j = 1; j <= 4; j++){
				ArrayList<TA> freeTAs = new ArrayList<TA>();
				for (TA ta : taList){
					if (ta.getDay(i).checkBlock(j))
						freeTAs.add(ta);
				}
				dayMap.put(j, freeTAs);
			}
			taMap.put(i, dayMap);
			
		}
		return taMap;
		
	}
	
	public static ArrayList<Course> createSchedule(HashMap<Integer,HashMap<Integer,ArrayList<TA>>> taMap, ArrayList<Course> classList){
		boolean gradOnly = true;
		for (int i = 0; i <= 1; i++){
		for (Course course : classList){
			for (Section section : course.getSections()){
				if (section.getTeacher() == null){
					ArrayList<TA> visited = new ArrayList<TA>();
					assignTA(section,taMap,visited,1,gradOnly,classList);
				}
			}
		}
		
		for (Course course : classList){
		for (Section section : course.getSections()){
			if (section.getTeacher() == null){
				ArrayList<TA> visited = new ArrayList<TA>();
				assignTA(section,taMap,visited,2,gradOnly,classList);
				}
			}
		}
		
		
		for (Course course : classList){
		for (Section section : course.getSections()){
			if (section.getTeacher() == null){
				ArrayList<TA> visited = new ArrayList<TA>();
				assignTA(section,taMap,visited,3,gradOnly,classList);
				}
			}
		} 
		gradOnly = false;
	}
		
		return classList;
	}
	
	private static boolean assignTA(Section section,HashMap<Integer,HashMap<Integer,ArrayList<TA>>> taMap,ArrayList<TA> visited,
			int pref, boolean gradOnly, ArrayList<Course> schedule){
		for (TA ta : taMap.get(section.getDay()).get(section.getBlock())){
			if (ta.getStatus() > 0 && preference(ta,pref,section) && noOverlap(ta.getTeaching(),section)){
			if (gradOnly){
				if (ta.isFullTA() == false)
					continue;
			}
			ta.setStatus(ta.getStatus() - 1);
			ta.getTeaching().add(section);
			section.setTeacher(ta);
			return true;
			}	
		}

		// failure condition 1: all TAs are full ? - Take first TA and reroute his availability
		for (TA ta :taMap.get(section.getDay()).get(section.getBlock())){
			if (visited.contains(ta) || !(preference(ta,pref,section)))
				continue;
			if (gradOnly){
				if (ta.isFullTA() == false)
					continue;
			}

			visited.add(ta);
			if (rerouteTA(section,ta,taMap,visited,pref,gradOnly,schedule))
				return true;
			//visited.remove(ta);
		}		
		//exception?
		return false;
	}
		
	private static boolean rerouteTA(Section curr, TA ta, HashMap<Integer, HashMap<Integer, ArrayList<TA>>> taMap,ArrayList<TA> visited,int pref, boolean gradOnly, ArrayList<Course> schedule) {

		
		boolean onlyGrad = true;
	for (int j = 0; j <= 1; j++){
		for (int i = 1; i <= pref; i++){
		if (assignTA(ta.getTeaching().get(0),taMap,visited,i,onlyGrad,schedule)){
			ta.getTeaching().set(0, curr);
			curr.setTeacher(ta);
			return true;
		}
		else if (ta.getTeaching().size() > 1){
			if (assignTA(ta.getTeaching().get(1),taMap,visited,i,onlyGrad,schedule)){
				ta.getTeaching().set(1, curr);
				curr.setTeacher(ta);
				return true;
			}			
		}
		}
	onlyGrad = gradOnly;	
	}
		return false;
	} 


	private static boolean preference(TA ta, int pref, Section section) {
		switch (pref){
		
		case 1: return (section.getCourse().equals(ta.getPref1()));
			
		case 2:	return (section.getCourse().equals(ta.getPref1()) || section.getCourse().equals(ta.getPref2()));
			
		case 3:	return (section.getCourse().equals(ta.getPref1()) || section.getCourse().equals(ta.getPref2())
				|| section.getCourse().equals(ta.getPref3()));
		
		
		}
		return false;
	}
	private static boolean noOverlap(ArrayList<Section> teaching, Section section) {
		for (Section taSection : teaching){
			if (taSection.getDay() == section.getDay())
				if (taSection.getBlock() == section.getBlock())
					return false;
		}
		return true;
	}

}
