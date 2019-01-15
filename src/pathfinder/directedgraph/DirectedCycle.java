package pathfinder.directedgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pathfinder.directedgraph.exceptions.GraphFormatException;

/**
 * class to use depth-first search algorithms to find all cycles in a graph
 * @author jimin
 *
 */
public class DirectedCycle {
	
	private DirectedGraph graph;
	
	private List<CyclicPath> allCycles;
	
	public DirectedGraph getGraph() {
		return graph;
	}
	
	public DirectedCycle(DirectedGraph graph) {
		this.graph = graph;
		
		Map<Character, Boolean> checked = new HashMap<Character, Boolean>();
		Map<Character, Boolean> onStack = new HashMap<Character, Boolean>();
		Map<Character, Character> reachingNodes = new HashMap<Character, Character>();
		
		allCycles = new ArrayList<CyclicPath>();
		
		for (Node node: graph.getAllNodes()) {
			checked.clear();
			onStack.clear();
			reachingNodes.clear();
			if (!checked.containsKey(node.getName()) || !checked.get(node.getName())) {
				dfs(node.getName(), checked, onStack, reachingNodes);
			}
		}
		
	}
	
	/**
	 * depth-first search to find the cycles 
	 * @param current
	 */
	private void dfs(char current, Map<Character, Boolean> checked, Map<Character, Boolean> onStack,
			Map<Character, Character> reachingNodes) {
		checked.put(current, true);
		onStack.put(current, true);
		
		for (Edge edge: graph.getAdjacentEdges(current)) {
			char next = edge.getDst();
			if (!checked.containsKey(next) || !checked.get(next)) {
				reachingNodes.put(next, current);
				dfs(next, checked, onStack, reachingNodes);
			} else if (onStack.containsKey(next) && onStack.get(next)) {
				LinkedList<Character> newCycle = new LinkedList<Character>();
				for (char curr = current; curr != next; curr = reachingNodes.get(curr)) {
					newCycle.push(curr);
				}
				newCycle.push(next);
				CyclicPath newCyclicPath = new CyclicPath(newCycle);
				if (!allCycles.contains(newCyclicPath)) {					
					allCycles.add(newCyclicPath);
				}
			}
		}
		
		onStack.put(current, false);
	}
	
	public boolean hasCycle() {
		return !allCycles.isEmpty();
	}
	
	public List<CyclicPath> getAllCycles() {
		return allCycles;
	}
	
	public static void main(String[]args) {
		
		try {
			DirectedGraph graph = new DirectedGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
			
			DirectedCycle dcycle = new DirectedCycle(graph);
			System.out.println(dcycle.hasCycle());
			
			for (Path cycle: dcycle.getAllCycles()) {
				System.out.println(cycle);
			}
			
			DirectedGraph g2 = new DirectedGraph("AB1, AC4, BC2, BA3"); 
			DirectedCycle bg2 = new DirectedCycle(g2);
			List<CyclicPath> paths2 = bg2.getAllCycles();  
			
			for (CyclicPath path: paths2) {
				System.out.println("HAHA--- "+path);
			}
			
			DirectedGraph g3 = new DirectedGraph("AB1, AD4, BE2, DE3, EC2, EA6"); 
			DirectedCycle bg3 = new DirectedCycle(g3);
			List<CyclicPath> paths3 = bg3.getAllCycles();  
			
			for (CyclicPath path: paths3) {
				System.out.println("HAHA3--- "+path);
			}
			
		} catch (GraphFormatException e) {
			e.printStackTrace();
		}
	}
	
}
