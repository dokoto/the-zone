package engine.logic.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

public class GraphCycleDetection<T> implements Iterable<T>
{

	/*
	 * A map from nodes in the graph to sets of outgoing edges. Each set of
	 * edges is represented by a map from edges to doubles.
	 */
	private final Map<T, Map<T, Double>> graph = new HashMap<T, Map<T, Double>>();

	/**
	 * Adds a new node to the graph. If the node already exists then its a
	 * no-op.
	 * 
	 * @param node
	 *            Adds to a graph. If node is null then this is a no-op.
	 * @return true if node is added, false otherwise.
	 */
	public boolean addNode(T node)
	{
		if (node == null)
		{
			throw new NullPointerException("The input node cannot be null.");
		}
		if (graph.containsKey(node))
			return false;

		graph.put(node, new HashMap<T, Double>());
		return true;
	}

	/**
	 * Given the source and destination node it would add an arc from source to
	 * destination node. If an arc already exists then the value would be
	 * updated the new value.
	 * 
	 * @param source
	 *            the source node.
	 * @param destination
	 *            the destination node.
	 * @param length
	 *            if length if
	 * @throws NullPointerException
	 *             if source or destination is null.
	 * @throws NoSuchElementException
	 *             if either source of destination does not exists.
	 */
	public void addEdge(T source, T destination, double length)
	{		
		if (source == null || destination == null)
		{
			throw new NullPointerException("Source and Destination, both should be non-null.");
		}

		if (!graph.containsKey(source) || !graph.containsKey(destination))
		{
			throw new NoSuchElementException("Source and Destination, both should be part of graph");
		}

		/* A node would always be added so no point returning true or false */
		graph.get(source).put(destination, length);
	}

	/**
	 * Removes an edge from the graph.
	 * 
	 * @param source
	 *            If the source node.
	 * @param destination
	 *            If the destination node.
	 * @throws NullPointerException
	 *             if either source or destination specified is null
	 * @throws NoSuchElementException
	 *             if graph does not contain either source or destination
	 */
	public void removeEdge(T source, T destination)
	{
		if (source == null || destination == null)
		{
			throw new NullPointerException("Source and Destination, both should be non-null.");
		}
		if (!graph.containsKey(source) || !graph.containsKey(destination))
		{
			throw new NoSuchElementException("Source and Destination, both should be part of graph");
		}
		graph.get(source).remove(destination);
	}

	/**
	 * Given a node, returns the edges going outward that node, as an immutable
	 * map.
	 * 
	 * @param node
	 *            The node whose edges should be queried.
	 * @return An immutable view of the edges leaving that node.
	 * @throws NullPointerException
	 *             If input node is null.
	 * @throws NoSuchElementException
	 *             If node is not in graph.
	 */
	public Map<T, Double> edgesFrom(T node)
	{
		if (node == null)
		{
			throw new NullPointerException("The node should not be null.");
		}
		Map<T, Double> edges = graph.get(node);
		if (edges == null)
		{
			throw new NoSuchElementException("Source node does not exist.");
		}
		return Collections.unmodifiableMap(edges);
	}

	/**
	 * Returns the iterator that travels the nodes of a graph.
	 * 
	 * @return an iterator that travels the nodes of a graph.
	 */
	@Override
	public Iterator<T> iterator()
	{
		return graph.keySet().iterator();
	}
}
