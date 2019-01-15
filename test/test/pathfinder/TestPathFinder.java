package test.pathfinder;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pathfinder.PathFinder;
import pathfinder.directedgraph.DirectedGraph;
import pathfinder.directedgraph.exceptions.NoSuchRouteException;
import junit.framework.TestCase;

public class TestPathFinder extends TestCase {
	
	PathFinder road;

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		DirectedGraph graph = new DirectedGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
		road = new PathFinder(graph);
	}

	@Test
	public void testGetDistanceForRoute() {
		try {
			int dist = road.getDistanceForRoute("A-B-C");
			assertEquals(9, dist);
		} catch (NoSuchRouteException e) {
			fail(e.getMessage());
		}
		try {
			int dist = road.getDistanceForRoute("A-DB-C");
			fail("can't be "+dist);
		} catch (NoSuchRouteException e) {
			assertNotNull(e);
		}
	}

	@Test
	public void testFindRoutesWithMaxStops() {
		List<String> routes = road.findRoutesWithMaxStops('C', 'C', 3);
		assertEquals(2, routes.size());
	}

	@Test
	public void testFindRoutesWithExactStops() {
		List<String> routes = road.findRoutesWithExactStops('A', 'C', 4);
		assertEquals(3, routes.size());
	}

	@Test
	public void testFindShortestRouteDistance() {
		try {
			int dist = road.findShortestRouteDistance('A', 'C');
			assertEquals(9, dist);
			int distc = road.findShortestRouteDistance('B', 'B');
			assertEquals(9, distc);
		} catch (NoSuchRouteException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testFindRoutesWithMaxDistance() {
		List<String> routes = road.findRoutesWithExactStops('C', 'C', 30);
		assertEquals(7, routes.size());
	}

}
