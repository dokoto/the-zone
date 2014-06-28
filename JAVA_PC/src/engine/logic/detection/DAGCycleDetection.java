package engine.logic.detection;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import engine.logic.model.GraphCycleDetection;

/*
 * O (V + E)
 * http://stackoverflow.com/questions/6850357/explanation-of-runtimes-of-bfs-and-dfs
 */
public final class DAGCycleDetection
{

	private DAGCycleDetection()
	{
	}

	/**
	 * Returns true if graph contains a cycle else returns false
	 * 
	 * @param graph
	 *            the graph to check for cycle
	 * @return true if cycle exists, else false.
	 */
	public static <T> boolean cycle(GraphCycleDetection<T> graph, Set<T> connectedNodes)
	{
		final Set<T> visitedNodes = new HashSet<T>();
		final Set<T> completedNodes = new HashSet<T>();

		for (T node : graph)
		{
			if (dfs(graph, node, visitedNodes, completedNodes, connectedNodes))
				return true;
		}
		return false;
	}

	/**
	 * Returns true if graph contains a cycle else returns false
	 * 
	 * 
	 * @param graph
	 *            the graph, which should be checked for cycles
	 * @param node
	 *            the current node whose edges should be traversed.
	 * @param visitedNodes
	 *            the nodes visited so far.
	 * @return true if graph contains a cycle else return false;
	 */
	private static <T> boolean dfs(GraphCycleDetection<T> graph, T node, Set<T> visitedNodes, Set<T> completedNodes, Set<T> connectedNodes)
	{		
		assert graph != null;
		assert node != null;
		assert visitedNodes != null;

		if (visitedNodes.contains(node))
		{
			if (completedNodes.contains(node))
				return false;
			else
			{		
				return true;
			}
		}

		visitedNodes.add(node); // constitues O(1) for each vertex

		for (Entry<T, Double> entry : graph.edgesFrom(node).entrySet())
		{
			if (dfs(graph, entry.getKey(), visitedNodes, completedNodes, connectedNodes))
			{
				connectedNodes.add(entry.getKey());
				return true;
			}
		}
		
		completedNodes.add(node);

		return false;
	}

}
