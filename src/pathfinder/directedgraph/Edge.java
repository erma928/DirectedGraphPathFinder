package pathfinder.directedgraph;

public class Edge {
	
	private char src;
	private char dst;
	private int weight;
	
	public Edge(char src, char dst, int weight) {
		this.src = src;
		this.dst = dst;
		this.weight = weight;
	}
	
	public char getSrc() {
		return src;
	}
	public char getDst() {
		return dst;
	}
	public int getWeight() {
		return weight;
	}

}
