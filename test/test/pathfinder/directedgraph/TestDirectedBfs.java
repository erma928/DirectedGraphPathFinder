package test.pathfinder.directedgraph;

import org.junit.Before;
import org.junit.Test;

import pathfinder.directedgraph.DirectedBfs;
import pathfinder.directedgraph.DirectedGraph;
import junit.framework.TestCase;

public class TestDirectedBfs extends TestCase {

	DirectedGraph graph;
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		graph = new DirectedGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
	}

	@Test
	public void testGetShortestDistanceTo() {
		DirectedBfs bfs = new DirectedBfs(graph, 'A');
		assertEquals(1, bfs.getShortestDistanceTo('D'));
	}

	@Test
	public void testGetShortestPathTo() {
		DirectedBfs bfs = new DirectedBfs(graph, 'A');
		assertTrue(bfs.getShortestPathTo('D').contains('A'));
	}

}
