package pathfinder.directedgraph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * class to use breadth-first search to find the shortest distance from src to dst based on
 * number of stops; not useful in pathfinder application, therefore deprecated.
 * @author jimin
 *
 */
@Deprecated
public class DirectedBfs {
	private char src;
	private DirectedGraph graph;
	
	private Map<Character, Boolean> reachable;
	private Map<Character, Character> reachingNodes;
	private Map<Character, Integer> shortestStops;
	
	public char getSrc() {
		return src;
	}

	public DirectedGraph getGraph() {
		return graph;
	}

	public DirectedBfs(DirectedGraph graph, char src) {
		this.graph = graph;
		this.src = src;
		
		reachable = new HashMap<Character, Boolean>();
		reachingNodes = new HashMap<Character, Character>();
		shortestStops = new HashMap<Character, Integer>();
		
		bfs(src);
	}
	
	private void bfs(char src) {
		Queue<Character> queue = new LinkedList<Character>();
		
		reachable.put(src, true);
		shortestStops.put(src, 0);
		queue.add(src);
		
		while(!queue.isEmpty()) {
			char current = queue.poll();
			
			for (Edge edge: graph.getAdjacentEdges(current)) {
				char next = edge.getDst();
				if (!reachable.containsKey(next) || !reachable.get(next)) {
					reachingNodes.put(next, current);
					shortestStops.put(next, shortestStops.get(current) + 1);
					reachable.put(next, true);
					queue.add(next);
				}
			}
		}
		
	}
	
	public boolean hasPathTo(char dst) {
		if (!reachable.containsKey(dst)) {
			return false;
		}
		return reachable.get(dst);
	}
	
	public int getShortestDistanceTo(char dst) {
		return shortestStops.get(dst);
	}
	
	public List<Character> getShortestPathTo(char dst) {
		if (!hasPathTo(dst)) {
			return null;
		}
		LinkedList<Character> path = new LinkedList<Character>();
		
		char current = dst;
		
		while (current!=src) {
			path.add(current);
			current = reachingNodes.get(current);
		}
		path.add(src);
		
		return path;		
	}
	
}
