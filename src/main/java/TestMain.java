import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestMain {
	 public static void printCurrentList(ArrayList<Class> schedule) {
		 for (Class x : schedule){
				System.out.println(x.toString());
				for (Section y : x.sections){
					System.out.println(y.day + " " + y.block + " " + y.teacher);
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
	
	public static ArrayList<Class> createSchedule(HashMap<Integer,HashMap<Integer,ArrayList<TA>>> taMap, ArrayList<Class> classList){
		boolean gradOnly = true;
		for (int i = 0; i <= 1; i++){
		for (Class course : classList){
			for (Section section : course.sections){
				if (section.teacher == null){
					ArrayList<TA> visited = new ArrayList<TA>();
					assignTA(section,taMap,visited,1,gradOnly,classList);
				}
			}
		}
		
		for (Class course : classList){
		for (Section section : course.sections){
			if (section.teacher == null){
				ArrayList<TA> visited = new ArrayList<TA>();
				assignTA(section,taMap,visited,2,gradOnly,classList);
				}
			}
		}
		
		
		for (Class course : classList){
		for (Section section : course.sections){
			if (section.teacher == null){
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
			int pref, boolean gradOnly, ArrayList<Class> schedule){
		for (TA ta : taMap.get(section.day).get(section.block)){
			if (ta.status > 0 && preference(ta,pref,section) && noOverlap(ta.teaching,section)){
			if (gradOnly){
				if (ta.fullTA == false)
					continue;
			}
			ta.status -= 1;
			ta.teaching.add(section);
			section.teacher = ta;
			return true;
			}	
		}

		// failure condition 1: all TAs are full ? - Take first TA and reroute his availability
		for (TA ta :taMap.get(section.day).get(section.block)){
			if (visited.contains(ta) || !(preference(ta,pref,section)))
				continue;
			if (gradOnly){
				if (ta.fullTA == false)
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
		
	private static boolean rerouteTA(Section curr, TA ta, HashMap<Integer, HashMap<Integer, ArrayList<TA>>> taMap,ArrayList<TA> visited,int pref, boolean gradOnly, ArrayList<Class> schedule) {

		
		boolean onlyGrad = true;
	for (int j = 0; j <= 1; j++){
		for (int i = 1; i <= pref; i++){
		if (assignTA(ta.teaching.get(0),taMap,visited,i,onlyGrad,schedule)){
			ta.teaching.set(0, curr);
			curr.teacher = ta;
			return true;
		}
		else if (ta.teaching.size() > 1){
			if (assignTA(ta.teaching.get(1),taMap,visited,i,onlyGrad,schedule)){
				ta.teaching.set(1, curr);
				curr.teacher = ta;
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
		
		case 1: return (section.course.equals(ta.pref1));
			
		case 2:	return (section.course.equals(ta.pref1) || section.course.equals(ta.pref2));
			
		case 3:	return (section.course.equals(ta.pref1) || section.course.equals(ta.pref2)|| section.course.equals(ta.pref3));
		
		
		}
		return false;
	}
	private static boolean noOverlap(ArrayList<Section> teaching, Section section) {
		for (Section taSection : teaching){
			if (taSection.day == section.day)
				if (taSection.block == section.block)
					return false;
		}
		return true;
	}

}
