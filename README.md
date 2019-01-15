###INSRUCTIONS TO RUN

1. under the DirectedGraphPathFinder/ directory, run 'ant'
2. `java -cp DirectedGraphPathFinder.jar pathfinder.PathFinder <path to file>`
3. in the input file, you can specify any graph format similar to `"AB9, BC7, CD7, DC5, DE5, AD7, CE3, EB5, AE8"`
in the file; but please use `'A','B'..'E'..` etc as graph node, as those are used in the main class

 
###PROGRAM DESIGN

1. Directed graph: 
	it is using a data structure similar to adjacent list for storing graph node and edges; 
	nodes are represented by single character; edges are weighted.
2. Graph search algorithm: 
	it is mainly using DFS to find all paths from source node to destination; it is also 
	using DFS to find all cycles in the graph; paths containing cycles are computed by combing
	acyclic path with cycles; certain case where a node are contained in multiples cycles could 
	fail to find certain paths, as in output #10.
3. More info:
	please refer to the code and documentations.