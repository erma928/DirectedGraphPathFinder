package pathfinder.directedgraph;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import pathfinder.directedgraph.exceptions.GraphFormatException;
import pathfinder.directedgraph.exceptions.InvalidPathException;
import pathfinder.directedgraph.exceptions.NoSuchRouteException;

/**
 * The <tt>DirectedGraph</tt> class represents a weighted directed graph; The graph vertices
 * are represented by single character like 'A', 'B', and edge weight max of 8 digits. 
 * @author jimin
 *
 */
public class DirectedGraph {
	
	protected static final String EDGE_REGEX = "\\w\\w\\d{1,8}"; 

	private Map<Character, Node> nodeMap;
	
	public DirectedGraph(InputStream input) throws GraphFormatException {
		nodeMap = new HashMap<Character, Node>();
		Scanner scanner = new Scanner(input).useDelimiter(",");
		try {
			while (scanner.hasNext()) {
				addEdge(scanner.next().trim());
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
	}
	
	public DirectedGraph(String graph) throws GraphFormatException {
		nodeMap = new HashMap<Character, Node>();
		String[] edges = graph.split(",");
		
		for (String edge: edges) {
			addEdge(edge.trim());
		}
	}
	
	private void addEdge(String edge) throws GraphFormatException {
		if (edge.matches(EDGE_REGEX)) {
			char src = edge.charAt(0);
			char dst = edge.charAt(1);
			int weight = Integer.parseInt(edge.substring(2));
			
			Node srcNode = findOrCreateNode(src);
			Node dstNode = findOrCreateNode(dst);
			
			Edge edgeNode = new Edge(src, dst, weight);
			srcNode.addEdge(edgeNode);
			
			nodeMap.put(src, srcNode);
			nodeMap.put(dst, dstNode);
			
		} else {
			throw new GraphFormatException();
		}
	}
	
	private Node findOrCreateNode(char src) {
		if (!nodeMap.containsKey(src)) {
			return new Node(src);
		} else {
			return nodeMap.get(src);
		}
	}
	
	public int getNumberOfVertices() {
		return nodeMap.size();
	}
	
	public int getNumberOfEdges() {
		int total = 0;
		
		for (Node node: nodeMap.values()) {
			total += node.getNumberOfAdjacentEdges();
		}
		
		return total;
	}
	
	public Iterable<Edge> getAdjacentEdges(char nodeKey) {
		Node node = nodeMap.get(nodeKey);
		
		return node.getAdjacentEdges();
	}
	
	public List<Node> getAllNodes() {
		List<Node> nodes = new ArrayList<Node>();
		for (Node entry: nodeMap.values()) {
			nodes.add(entry);
		}
		
		return nodes;
	}
	
	public int getDistanceForPath(List<Character> vertices) throws NoSuchRouteException {
		int distance = 0;
		for (int i=1; i<vertices.size(); i++) {
			char curr = vertices.get(i-1);
			char next = vertices.get(i);
			Node currNode = nodeMap.get(curr);
			Edge edge = currNode.getEdgeTo(next);
			
			if (edge==null) {
				throw new NoSuchRouteException();
			} else {
				distance += edge.getWeight();
			}
			
		}
		
		return distance;
	}
	
	public int getDistanceForPath(Path path) {
		try {
			List<Character> nodeList = new ArrayList<Character>(path.getNodeList());
			if (!nodeList.isEmpty() && path instanceof CyclicPath) {
				nodeList.add(nodeList.get(0));
			}
			return getDistanceForPath(nodeList);
		} catch (NoSuchRouteException e) {
			throw new InvalidPathException();
		}
	}
	
}
