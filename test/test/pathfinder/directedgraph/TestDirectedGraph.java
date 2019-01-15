package test.pathfinder.directedgraph;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import pathfinder.directedgraph.CyclicPath;
import pathfinder.directedgraph.DirectedGraph;
import pathfinder.directedgraph.Path;
import pathfinder.directedgraph.exceptions.GraphFormatException;
import pathfinder.directedgraph.exceptions.NoSuchRouteException;
import junit.framework.TestCase;

public class TestDirectedGraph extends TestCase {

	@Test
	public void testDirectedGraphString() {
		try {
			DirectedGraph graph = new DirectedGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
			assertNotNull(graph);
		} catch (GraphFormatException e) {
			fail(e.getMessage());
		}
		try {
			new DirectedGraph("AB5, BCFAI4");
			fail("Wrong edge format");
		} catch (GraphFormatException e) {
			assertNotNull(e);
		}
	}

	@Test
	public void testGetNumberOfVertices() {
		DirectedGraph graph;
		try {
			graph = new DirectedGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
			assertEquals(5, graph.getNumberOfVertices());
		} catch (GraphFormatException e) {
			fail("Wrong edge format");
		}
	}

	@Test
	public void testGetNumberOfEdges() {
		DirectedGraph graph;
		try {
			graph = new DirectedGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
			assertEquals(9, graph.getNumberOfEdges());
		} catch (GraphFormatException e) {
			fail("Wrong edge format");
		}
	}

	@Test
	public void testGetAdjacentEdges() {
		DirectedGraph graph;
		try {
			graph = new DirectedGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
			assertEquals('A', graph.getAdjacentEdges('A').iterator().next().getSrc());
		} catch (GraphFormatException e) {
			fail("Wrong edge format");
		}
	}

	@Test
	public void testGetAllNodes() {
		DirectedGraph graph;
		try {
			graph = new DirectedGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
			assertEquals(5, graph.getAllNodes().size());
		} catch (GraphFormatException e) {
			fail("Wrong edge format");
		}
	}

	@Test
	public void testGetDistanceForPathListOfCharacter() {
		DirectedGraph graph;
		try {
			graph = new DirectedGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
			List<Character> cycleList = Arrays.asList('B', 'C', 'E');
			assertEquals(6, graph.getDistanceForPath(cycleList));
		} catch (GraphFormatException e) {
			fail(e.getMessage());
		} catch (NoSuchRouteException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetDistanceForPathPath() {
		DirectedGraph graph;
		try {
			graph = new DirectedGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
			List<Character> cycleList = Arrays.asList('B', 'C', 'E');
			Path path = new Path(cycleList);
			assertEquals(6, graph.getDistanceForPath(path));
		} catch (GraphFormatException e) {
			fail(e.getMessage());
		} 
	}
	
	@Test
	public void testGetDistanceForPathCycle() {
		DirectedGraph graph;
		try {
			graph = new DirectedGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
			List<Character> cycleList = Arrays.asList('B', 'C', 'E');
			CyclicPath path = new CyclicPath(cycleList);
			assertEquals(9, graph.getDistanceForPath(path));
		} catch (GraphFormatException e) {
			fail(e.getMessage());
		} 
	}

}
