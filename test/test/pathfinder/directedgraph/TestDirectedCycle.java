package test.pathfinder.directedgraph;

import org.junit.Before;
import org.junit.Test;

import pathfinder.directedgraph.DirectedCycle;
import pathfinder.directedgraph.DirectedGraph;
import junit.framework.TestCase;

public class TestDirectedCycle extends TestCase {

	DirectedGraph graph;
	DirectedCycle cycleFinder;
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		graph = new DirectedGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
		cycleFinder = new DirectedCycle(graph);
	}

	@Test
	public void testHasCycle() {
		boolean has = cycleFinder.hasCycle();
		assertTrue(has);
	}

	@Test
	public void testGetAllCycles() {
		assertEquals(2, cycleFinder.getAllCycles().size());
	}

}
