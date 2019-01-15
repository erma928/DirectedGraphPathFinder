package pathfinder.directedgraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import pathfinder.directedgraph.exceptions.NoSuchRouteException;

/**
 * class to use depth-first search algorithms to find all acyclic paths from src to destination;
 * it is also getting shortest path by comparing all the acyclic paths
 * @author jimin
 *
 */
public class DirectedDfs {
	private char src;
	private DirectedGraph graph;
	
	public char getSrc() {
		return src;
	}

	public DirectedGraph getGraph() {
		return graph;
	}

	public DirectedDfs(DirectedGraph graph, char src) {
		this.graph = graph;
		this.src = src;
		
	}
	
	/**
	 * depth-first search to find all directed acyclic paths
	 * @param dst
	 * @param visited
	 * @param allPaths
	 */
	private void dfs(char dst, LinkedList<Character> visited, List<Path> allPaths) {
		char current = visited.getLast();
		for (Edge edge: graph.getAdjacentEdges(current)) {
			char next = edge.getDst();
			if (visited.contains(next)) {
				continue;
			}
			if (next==dst) {
				visited.add(next);
				addToPaths(visited, allPaths);
				visited.removeLast();
				break;
			}
		}
		for (Edge edge: graph.getAdjacentEdges(current)) {
			char next = edge.getDst();
			if (visited.contains(next) || next==dst) {
				continue;
			}
			visited.add(next);
			dfs(dst, visited, allPaths);
			visited.removeLast();
		}
	}
	
	private void addToPaths(LinkedList<Character> visited, List<Path> allPaths) {
		List<Character> newList = new LinkedList<Character>();
		for (char curr: visited) {
			newList.add(curr);
		}
		allPaths.add(new Path(newList));
	}
	
	public List<Path> getAllAcyclicPathsTo(char dst) {
		List<Path> allPaths = new ArrayList<Path>();
		LinkedList<Character> visited = new LinkedList<Character>();
		visited.add(src);
		dfs(dst, visited, allPaths);
		
		return allPaths;
	}
	
	public int getShortestDistance(char dst) throws NoSuchRouteException {
		if (src==dst) {
			return 0;
		}
		List<Path> allPaths = getAllAcyclicPathsTo(dst);
		if (allPaths.isEmpty()) {
			throw new NoSuchRouteException();
		}
		
		int shortest = Integer.MAX_VALUE;
		for (Path path: allPaths) {
			if (shortest>graph.getDistanceForPath(path)) {
				shortest = graph.getDistanceForPath(path);
			}
		}
		
		return shortest;
	}
	
}
