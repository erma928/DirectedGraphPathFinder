package pathfinder.directedgraph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * pure cyclic path; use node list like ['A', 'B', 'C'], implies there's an edge from 'C' to 'A'
 * @author jimin
 *
 */
public class CyclicPath extends Path {
	
	public CyclicPath(List<Character> nodeList) {
		super(nodeList);
	}
	
	public List<Character> getNodeList() {
		return nodeList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (char node: nodeList) {
			builder.append(node).append("-");
		}
		if (!nodeList.isEmpty()) {
			builder.append(nodeList.get(0));
			return builder.toString();
		}
		
		return builder.toString();
	}
	
	public CyclicPath extendWithCycle(CyclicPath cycle, int times) {
		return (CyclicPath)super.extendWithCycle(cycle, times);
	}
	
	@Override
	public boolean equals(Object anotherCycle) {
		if (anotherCycle==null || !(anotherCycle instanceof CyclicPath)) {
			return false;
		}
		CyclicPath cyclePath = (CyclicPath) anotherCycle;
		Set<Character> myNodes = new HashSet<Character>(nodeList);
		return myNodes.equals(new HashSet<Character>(cyclePath.getNodeList()));
	}
	
	public static void main(String[]args) {
		List<Character> nodeList = Arrays.asList('A', 'B', 'C', 'D');
		List<Character> cycleList = Arrays.asList('B', 'C', 'E');
		List<Character> cycleList1 = Arrays.asList('C', 'B', 'E');
		
		CyclicPath a = new CyclicPath(nodeList);
		CyclicPath c = new CyclicPath(cycleList);
		CyclicPath d = new CyclicPath(cycleList1);
		
		System.out.println(a);
		System.out.println(c.equals(d));
		System.out.println(a.extendWithCycle(c, 0));
		System.out.println(a.extendWithCycle(c, 1));
		System.out.println(a.extendWithCycle(c, 2));
	}
}
