package pathfinder.directedgraph;

import java.util.ArrayList;
import java.util.List;

public class PathFinder {
	private DirectedGraph graph;
	private DirectedCycle cycleFinder;
	
	public PathFinder(DirectedGraph graph) {
		this.graph = graph;
		cycleFinder = new DirectedCycle(graph);
	}
	
	public List<Path> findAllValidPaths(char src, char dst, PathValidator validator) {
		List<Path> results = new ArrayList<Path>();
		
		DirectedDfs dfs = new DirectedDfs(graph, src);
		List<Path> acyclicPaths = dfs.getAllAcyclicPathsTo(dst);
		List<CyclicPath> cycles = cycleFinder.getAllCycles();
		
		for (Path path: acyclicPaths) {
			int[] extendTimes = new int[cycles.size()];
//			extendPathWithCycles();
//			for (CyclicPath cycle: cycles) {
//				if (path.intersectWithCycle(cycle)) {
//					for (int times=1; path.numberOfStops()+times*cycle.numberOfStops()<=maxStops+1; times++) {
//						routes.add(path.extendWithCycle(cycle, times).toString()); 
//					}
//				}
//			}
//			
		}
		
		return results;
	}
	
	public void extendPathWithCycles(Path path, List<CyclicPath> cycles, int[] extendTimes, 
			PathValidator validator, List<Path> results) {
		Path tempPath = path;
		for (int i=0; i<extendTimes.length; i++) {
			tempPath = tempPath.extendWithCycle(cycles.get(i), extendTimes[i]);
		}
		
		if (validator.isValid(tempPath)) {
			// add it to results
			results.add(tempPath);
		}
		
		if (!validator.extensible(tempPath)) {
			return;
		}
		
		int index = 0;
		for (CyclicPath cycle: cycles) {
			index++;
			if (path.intersectWithCycle(cycle)) {
				extendTimes[index]++;
				extendPathWithCycles(path, cycles, extendTimes, validator, results);
			}
		}
	}

}
