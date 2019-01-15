package pathfinder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import pathfinder.directedgraph.CyclicPath;
import pathfinder.directedgraph.DirectedCycle;
import pathfinder.directedgraph.DirectedDfs;
import pathfinder.directedgraph.DirectedGraph;
import pathfinder.directedgraph.Path;
import pathfinder.directedgraph.exceptions.GraphFormatException;
import pathfinder.directedgraph.exceptions.NoSuchRouteException;

/**
 * the application class for DAG path planning; it is making use of
 * <tt>DirectedGraph</tt> data structure and the graph search algorithm classes;
 * it is combining acyclic path with cycles to get longer path; but in some cases 
 * where a node is inside multiple cycles, it may miss some paths.
 * for the purpose.
 * @author jimin
 *
 */
public class PathFinder {
	protected static final String ROUTE_REGEX="(\\w-)+\\w";
	protected static final String NO_ROUTE_MSG="NO SUCH ROUTE";
	private DirectedGraph graph;
	private DirectedCycle cycleFinder;
	
	public PathFinder(String filename) throws FileNotFoundException, GraphFormatException {
		InputStream input = new FileInputStream(filename);
		graph = new DirectedGraph(input);
		cycleFinder = new DirectedCycle(graph);
	}
	
	public PathFinder(DirectedGraph graph) {
		this.graph = graph;
		cycleFinder = new DirectedCycle(graph);
	}
	
	/**
	 * find distance for route
	 * @param route should be in a format like "A-B-C" etc
	 * @return
	 * @throws NoSuchRouteException
	 */
	public int getDistanceForRoute(String route) throws NoSuchRouteException {
		if (route.matches(ROUTE_REGEX)) {
			String[] stops = route.split("-");
			List<Character> vertices = new ArrayList<Character>();
			for (String stop: stops) {
				vertices.add(stop.charAt(0));
			}
			return graph.getDistanceForPath(vertices);
		} else {
			throw new NoSuchRouteException();
		}
	}
	
	/**
	 * find all routes from src to dst, with maximum number of stops;
	 * combine acyclic path with cycles to get longer paths
	 * @param src
	 * @param dst
	 * @param maxStops
	 * @return
	 */
	public List<String> findRoutesWithMaxStops(char src, char dst, int maxStops) {
		List<String> routes = new ArrayList<String>();
		List<CyclicPath> cycles = cycleFinder.getAllCycles();
		
		if (src==dst) { // if src and destination are the same
			for (CyclicPath cycle: cycles) {
				if (cycle.containsNode(src)) {
					if (cycle.numberOfStops()>maxStops) {
						continue;
					}
					for (int times=1; times*cycle.numberOfStops()<=maxStops; times++) {
						routes.add(((CyclicPath)cycle.extendWithCycle(cycle, times-1)).toString()); 
					}
				}
			}
			
			return routes;
		}
		
		// if src and destination are different
		DirectedDfs dfs = new DirectedDfs(graph, src);
		List<Path> acyclicPaths = dfs.getAllAcyclicPathsTo(dst);
		
		for (Path path: acyclicPaths) {
			if (path.numberOfStops()>maxStops+1) {
				continue; // path exceeds max stops
			}
			for (CyclicPath cycle: cycles) {
				if (path.intersectWithCycle(cycle)) {
					for (int times=1; path.numberOfStops()+times*cycle.numberOfStops()<=maxStops+1; times++) {
						routes.add(path.extendWithCycle(cycle, times).toString()); 
					}
				}
			}
			
		}
		
		return routes;
	}

	/**
	 * find all routes from src to dst, with exact number of stops;
	 * combine acyclic path with cycles to do to get longer paths
	 * @param src
	 * @param dst
	 * @param numStops
	 * @return
	 */
	public List<String> findRoutesWithExactStops(char src, char dst, int numStops) {
		List<String> routes = new ArrayList<String>();
		List<CyclicPath> cycles = cycleFinder.getAllCycles();
		
		if (src==dst) { // if src and destination are the same
			for (CyclicPath cycle: cycles) {
				if (cycle.containsNode(src)) {
					int diff = numStops+1 - cycle.numberOfStops();
					if (diff<0) {
						continue; // path exceeds stops
					}
					if (diff==0) {
						routes.add(cycle.toString()); 
						continue;
					} 
					if (diff%cycle.numberOfStops()==0) {
						int times = diff/cycle.numberOfStops();
						routes.add(cycle.extendWithCycle(cycle, times).toString()); 
					}
				}
			}
			
			return routes;
		}

		DirectedDfs dfs = new DirectedDfs(graph, src);
		List<Path> acyclicPaths = dfs.getAllAcyclicPathsTo(dst);
		
		for (Path path: acyclicPaths) {
			int diff = numStops+1 - path.numberOfStops();
			if (diff<0) {
				continue; // path exceeds stops
			}
			if (diff==0) {
				routes.add(path.toString()); 
				continue;
			} 
			
			for (CyclicPath cycle: cycles) {
				if (path.intersectWithCycle(cycle)) {
					if (diff%cycle.numberOfStops()==0) {
						int times = diff/cycle.numberOfStops();
						routes.add(path.extendWithCycle(cycle, times).toString()); 
					}
				}
			}
			
		}
		
		return routes;
	}
	
	/**
	 * find shortest route distance from src to dst; when src is the same as dst, 
	 * return the shortest cycle distance, if no cycle for it, return 0 
	 * @param src
	 * @param dst
	 * @return
	 * @throws NoSuchRouteException
	 */
	public int findShortestRouteDistance(char src, char dst) throws NoSuchRouteException {
		if (src==dst) {
			List<CyclicPath> cycles = cycleFinder.getAllCycles();
			if (cycles.isEmpty()) {
				return 0;
			}
			int minCycleLength = Integer.MAX_VALUE;
			boolean inCycle = false;
			for (CyclicPath cycle: cycles) {
				if (cycle.containsNode(dst)) {
					inCycle = true;
					if (minCycleLength>graph.getDistanceForPath(cycle)) {
						minCycleLength = graph.getDistanceForPath(cycle);
					}
				}
			}
			if (inCycle) {
				return minCycleLength;
			}
			
			return 0;
			
		}
		DirectedDfs dfs = new DirectedDfs(graph, src);
		return dfs.getShortestDistance(dst);
	}
	
	/**
	 * find all routes from src to dst, with maximum distance;
	 * combine acyclic path with cycles to do to get longer paths
	 * @param src
	 * @param dst
	 * @param maxDistance
	 * @return
	 */
	public List<String> findRoutesWithMaxDistance(char src, char dst, int maxDistance) {
		List<String> routes = new ArrayList<String>();
		List<CyclicPath> cycles = cycleFinder.getAllCycles();
		
		if (src==dst) { // if src and destination are the same
			for (CyclicPath cycle: cycles) {
				if (cycle.containsNode(src)) {
					if (graph.getDistanceForPath(cycle)>maxDistance) {
						continue;
					}
					for (int times=1; times*graph.getDistanceForPath(cycle)<=maxDistance; times++) {
						routes.add((cycle.extendWithCycle(cycle, times-1)).toString()); 
					}
				}
			}
			
			return routes;
		}
		
		DirectedDfs dfs = new DirectedDfs(graph, src);
		List<Path> acyclicPaths = dfs.getAllAcyclicPathsTo(dst);
		
		for (Path path: acyclicPaths) {
			if (graph.getDistanceForPath(path)>maxDistance) {
				continue; // path exceeds max stops
			}
			for (CyclicPath cycle: cycles) {
				if (path.intersectWithCycle(cycle)) {
					for (int times=1; graph.getDistanceForPath(path)+times*graph.getDistanceForPath(cycle)<=maxDistance+1; times++) {
						routes.add(path.extendWithCycle(cycle, times).toString()); 
					}
				}
			}
			
		}
		
		return routes;
	}
	
    public static void main(String [] args) {
		try {
			if (args.length<1) {
				System.out.println("Usage: java -cp PathFinder.jar pathfinder.PathFinder <path to file>");
				System.out.println("Or: java pathfinder.PathFinder <path to file>");
				return;
			}
			
			PathFinder rails = new PathFinder(args[0]);
			
	        // Output #1 The distance of the route A-B-C.
	    	try {
				System.out.println("Output #1: "+rails.getDistanceForRoute("A-B-C"));
			} catch (NoSuchRouteException e) {
				System.out.println("Output #1: "+NO_ROUTE_MSG);
			}
	        
	        // Output #2 The distance of the route A-D.
	    	try {
				System.out.println("Output #2: "+rails.getDistanceForRoute("A-D"));
			} catch (NoSuchRouteException e) {
				System.out.println("Output #2: "+NO_ROUTE_MSG);
			}
	
	        // Output #3 The distance of the route A-D-C.
	    	try {
				System.out.println("Output #3: "+rails.getDistanceForRoute("A-D-C"));
			} catch (NoSuchRouteException e) {
				System.out.println("Output #3: "+NO_ROUTE_MSG);
			}
	
	        // Output #4 The distance of the route A-E-B-C-D.
	    	try {
				System.out.println("Output #4: "+rails.getDistanceForRoute("A-E-B-C-D"));
			} catch (NoSuchRouteException e) {
				System.out.println("Output #4: "+NO_ROUTE_MSG);
			}
	    	
	        // Output #5 The distance of the route A-E-D.
	    	try {
				System.out.println("Output #5: "+rails.getDistanceForRoute("A-E-D"));
			} catch (NoSuchRouteException e) {
				System.out.println("Output #5: "+NO_ROUTE_MSG);
			}
	
	        // Output #6 The number of trips from C to C with a maximum of 3 stops
	    	System.out.println("Output #6: "+rails.findRoutesWithMaxStops('C', 'C', 3).size());
	        
	    	// Output #7 The number of trips from A to C with exactly 4 stops
	    	System.out.println("Output #7: "+rails.findRoutesWithExactStops('A', 'C', 4).size());
	    	
	        // Output #8 The length of the shortest route from A to C
	    	try {
				System.out.println("Output #8: "+rails.findShortestRouteDistance('A', 'C'));
			} catch (NoSuchRouteException e) {
				System.out.println("Output #8: "+NO_ROUTE_MSG);
			}
	    	
	        // Output #9 The length of the shortest route from B to B
	    	try {
				System.out.println("Output #9: "+rails.findShortestRouteDistance('B', 'B'));
			} catch (NoSuchRouteException e) {
				System.out.println("Output #9: "+NO_ROUTE_MSG);
			}
	    	
	        // Output #10 The number of different routes from C to C with a distance of less than 30
	    	System.out.println("Output #10: "+rails.findRoutesWithMaxDistance('C', 'C', 30).size());
	    	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (GraphFormatException e) {
			e.printStackTrace();
		}
    }
}
