package engine.logic.model;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import Utils.Logg;

public class TerritoryMap extends GraphCycleDetection<Integer>
{
	private int rows, columns, numOfNodes;
	private Multimap<User, Point2D> usersEdges = null;
	private Multimap<Point2D, User> edgesUsers = null;
	private HashMap<Point2D, Edge> edges = null;
	private Set<User> users = null;
	
	
	public TerritoryMap (int rows, int columns)
	{	
		super();
		this.usersEdges = ArrayListMultimap.create();
		this.edgesUsers = ArrayListMultimap.create();	
		this.edges = new HashMap<Point2D, Edge>();
		this.users = new HashSet<User>();
		this.rows = rows;
		this.columns = columns;
		this.numOfNodes = rows * columns; 		
	}
	
	public boolean add(int source, int destination, User user)
	{
		Logg.INFO("User: " + user.toString() + " Moves : POINT[" + source + "," + destination + "]");
		Point2D tmpPoint = new Point2D.Double(source, destination);
		Edge tmpEdge = createEdge(source, destination, user);
		if (tmpEdge == null)
			return false;
		
		// Add edges by user		
		edges.put(tmpPoint, tmpEdge);
		usersEdges.put(user, tmpPoint);
		edgesUsers.put(tmpPoint, user);
		
		// Add edges to global Map
		this.addEdge(source, destination, 10);
		
		return true;		
	}
		
	private Edge createEdge(int source, int destination, User user)
	{
		if (users == null)
		{
			Logg.INFO("No users created");
			return null;	
		}
		
		if (users.contains(user) == false)
		{
			Logg.INFO("User notfound : " + user.toString());
			return null;
		}
		
		Edge tmpEdge = new Edge(source, destination, isEdgeInTheBorder(source, destination));
		Point2D tmpPoint = new Point2D.Double(source, destination);
						
		int numOfOccurrencies = edgesUsers.get(tmpPoint).size(); 
		if (numOfOccurrencies >= 2)		
		{
			Logg.INFO("This edge is selected by two users. Only two users can select the same edge : " + tmpPoint.toString());
			return null;
		}		
		
		if (numOfOccurrencies == 1 && tmpEdge.isInTheBorder())
		{
			Logg.INFO("This edge is in the border, it only one time can be selected : " + tmpPoint.toString());
			return null;
		}
		
		if (numOfOccurrencies == 1 && tmpEdge.isDiagonal() == false)
		{
			Logg.INFO("This edge selected is not a diagonal. Only diagonals can be select by two users : " + tmpPoint.toString());
			return null;
		}
		
		if (tmpEdge.isDiagonal())
			tmpEdge.setLock(true);
		
		return tmpEdge;
	}
	
	public boolean isEdgeInTheBorder(int source, int destination)
	{
		return !( source != 0 && destination != 0 && source != rows-1 && source != columns-1 && destination != rows-1 && destination != columns-1);
	}
	
	public User createNewUser(String name, Color color)
	{
		User tmpUser = new User(name, color);
		users.add(tmpUser);
		
		return tmpUser;
	}
		
	public static TerritoryMap createNodes(int rows, int columns)
	{
		TerritoryMap map = new TerritoryMap(rows, columns);
		for (int i = 0; i <= map.getNumOfNodes(); i++)
			map.addNode(i);

		return map;
	}	
	
	public ArrayList<Point2D> getSelectedEdgesAroundNode(int node, User user)
	{
		ArrayList<Point2D> edgesAroundNode = new ArrayList<Point2D>();
		
		for(Point2D point : usersEdges.get(user))
		{
			if (point.getX() == node || point.getY() == node)
				edgesAroundNode.add(point);
		}
		
		return edgesAroundNode;
	}
	
	public void removeEdges(Set<Integer> nodesToRemove, User user)
	{
		for (int node : nodesToRemove)
		{
			ArrayList<Point2D> edgesSelectedAroundNode = getSelectedEdgesAroundNode(node, user);
			//Logg.INFO("NODE: " + node + " edgesSelectedAroundNode :\n" + edgesSelectedAroundNode.toString());
			for (Point2D point : edgesSelectedAroundNode)
			{											
				deleteEdge(point, user);					
			}
		}
	}
		
	private Edge mkEdge(int source, int destination)
	{
		return new Edge(source, destination, this.isEdgeInTheBorder(source, destination));
	}
	
	public boolean isEdgeDoubleSelected(Edge edge)
	{
		return (edgesUsers.containsKey(mkEdge(edge.getDestination(), edge.getSource())));			
	}
	
	public void deleteEdge(Point2D point, User user)
	{
		super.removeEdge((int)point.getX(), (int)point.getY());		
		/*
		if (edgesUsers.remove(edge, userID) == false)
			Logg.INFO("Fail [edgesUsers] removing : " + edge.toString());
		if (usersEdges.remove(userID, edge) == false)
			Logg.INFO("Fail [usersEdges] removing : " + edge.toString());
		*/				
	}
		
	public int getRows()
	{
		return rows;
	}

	public int getColumns()
	{
		return columns;
	}

	public int getNumOfNodes()
	{
		return numOfNodes;
	}

}
