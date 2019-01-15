package pathfinder.directedgraph;

import java.util.LinkedHashSet;

/**
 * represented by char like 'A', 'B'; use LinkedHashSet to avoid duplicate
 * @author jimin
 *
 */
public class Node {

	private char name;

	private LinkedHashSet<Edge> edgeList;
	
	public Node(char name) {
		this.name = name;
		edgeList = new LinkedHashSet<Edge>();
	}
	
	public char getName() {
		return name;
	}
	
	public void addEdge(Edge edge) {
		if (name==edge.getSrc()) {
			edgeList.add(edge);
		}
	}
	
	public Iterable<Edge> getAdjacentEdges() {
		return edgeList;
	}
	
	public int getNumberOfAdjacentEdges() {
		return edgeList.size();
	}
	
	public Edge getEdgeTo(char dst) {
		for (Edge edge: edgeList) {
			if (edge.getDst()==dst) {
				return edge;
			}
		}
		return null;
	}
	
}
