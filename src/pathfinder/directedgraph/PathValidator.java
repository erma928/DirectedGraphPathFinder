package pathfinder.directedgraph;

public interface PathValidator {

	public boolean isValid(Path path);
	public boolean extensible(Path path);
	
}
