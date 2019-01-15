package pathfinder.directedgraph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import pathfinder.directedgraph.util.ListUtil;

/**
 * not pure cyclic path; may contain cycle in the middle
 * @author jimin
 *
 */
public class Path {
	List<Character> nodeList;
	
	public Path(List<Character> nodeList) {
		this.nodeList = nodeList;
	}
	
	public List<Character> getNodeList() {
		return nodeList;
	}
	
	/**
	 * extend the path a number of times
	 * @param cycle
	 * @param times
	 * @return
	 */
	public Path extendWithCycle(CyclicPath cycle, int times) {
		List<Character> newList = new LinkedList<Character>();
		List<Character> intersect = ListUtil.intersect(nodeList, cycle.getNodeList());
		List<Character> subtract = ListUtil.subtract(cycle.getNodeList(), nodeList);
		
		if (!intersect.isEmpty()) {
			int meetIndex = nodeList.indexOf(intersect.get(intersect.size()-1));
			newList.addAll(nodeList.subList(0, meetIndex+1));
			
			for (int i=0; i<times; i++) {
				newList.addAll(subtract);
				newList.addAll(intersect);
			}
			if (nodeList.size()>meetIndex+1) {
				newList.addAll(nodeList.subList(meetIndex+1, nodeList.size()));
			}
			
			if (this instanceof CyclicPath) {
				return new CyclicPath(newList);
			}
			
			return new Path(newList);
		}
		
		return this;
	}
	
	public boolean intersectWithCycle(CyclicPath cycle) {
		List<Character> intersect = ListUtil.intersect(nodeList, cycle.getNodeList());
		return !intersect.isEmpty();
	}
	
	public int numberOfStops() {
		return nodeList.size();
	}
	
	public boolean containsNode(char node) {
		return nodeList.contains(node);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (char node: nodeList) {
			builder.append(node).append("-");
		}
		if (builder.length()>0) {
			builder.deleteCharAt(builder.length() - 1);
		}
		return builder.toString();
	}
	
	public static void main(String[]args) {
		List<Character> nodeList = Arrays.asList('A', 'B', 'C', 'D');
		List<Character> cycleList = Arrays.asList('B', 'C', 'E');
		
		Path a = new Path(nodeList);
		CyclicPath c = new CyclicPath(cycleList);
		
		System.out.println(a);
		System.out.println(a.extendWithCycle(c, 0));
		System.out.println(a.extendWithCycle(c, 1));
		System.out.println(a.extendWithCycle(c, 2));
	}
}
