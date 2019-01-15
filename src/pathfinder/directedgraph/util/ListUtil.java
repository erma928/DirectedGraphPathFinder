package pathfinder.directedgraph.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

    public static <T> List<T> intersect(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }
    
    public static <T> List<T> subtract(List<T> list1, List<T> list2) {
    	List<T> list = new ArrayList<T>();
    	
    	for (T t : list1) {
    		if(!list2.contains(t)) {
    			list.add(t);
    		}
    	}
    	
    	return list;
    }
}
