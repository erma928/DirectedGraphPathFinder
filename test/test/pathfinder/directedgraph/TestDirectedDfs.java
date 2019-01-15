package test.pathfinder.directedgraph;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pathfinder.directedgraph.DirectedDfs;
import pathfinder.directedgraph.DirectedGraph;
import pathfinder.directedgraph.Path;
import pathfinder.directedgraph.exceptions.NoSuchRouteException;
import junit.framework.TestCase;

public class TestDirectedDfs extends TestCase {

	DirectedGraph graph;
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		graph = new DirectedGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
	}

	@Test
	public void testGetAllAcyclicPathsTo() {
		DirectedDfs dfs = new DirectedDfs(graph, 'A');
		List<Path> paths = dfs.getAllAcyclicPathsTo('C');
		assertEquals(4, paths.size());
		DirectedDfs dfs1 = new DirectedDfs(graph, 'C');
		List<Path> paths1 = dfs1.getAllAcyclicPathsTo('C');
		assertEquals(0, paths1.size());
	}

	@Test
	public void testGetShortestDistance() {
		DirectedDfs dfs = new DirectedDfs(graph, 'A');
		try {
			int dist = dfs.getShortestDistance('C');
			assertEquals(9, dist);
		} catch (NoSuchRouteException e) {
			fail(e.getMessage());
		}
		try {
			int dist = dfs.getShortestDistance('G');
			fail(""+dist);
		} catch (NoSuchRouteException e) {
			assertNotNull(e);
		}
	}

}
