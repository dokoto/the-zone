package engine.logic.model;

public class Edge
{
	private int source;
	private int destination;
	private boolean isDiagonal;
	public final static int DIAGONAL = 3;
	private TypeEdge type;
	private boolean isInTheBorder;
	private boolean lock;
	
	public static Edge createClone(Edge edge)
	{		
		Edge clone = new Edge();		
		clone.set(edge.source, edge.destination, edge.isInTheBorder, edge.isLock());		
		return clone;
	}
	
	public static Edge createCloneReverse(Edge edge)
	{		
		Edge clone = new Edge();		
		clone.set(edge.destination, edge.source, edge.isInTheBorder, edge.isLock());		
		return clone;
	}
	
	private Edge() 
	{
		this.lock = false;
	}
	
	
	public Edge(int source, int destination, boolean isInTheBorder)
	{				
		this.set(source, destination, isInTheBorder, false);
	}
	
	private boolean set(int source, int destination, boolean isInTheBorder, boolean lock)
	{
		this.source = source;
		this.destination = destination;
		this.isDiagonal = Edge.isDiagonal(this.source, this.destination);
		this.type = Edge.whatTypeIs(this.source, this.destination);
		this.isInTheBorder = isInTheBorder;		
		this.lock = lock;
		return true;
	}
			
	public int getSource()
	{
		return source;
	}
	
	public int getDestination()
	{
		return destination;
	}

	public static TypeEdge whatTypeIs(int source, int destination)
	{
		if (isDiagonal(source, destination) == true)
		{
			if (source < destination)
				return TypeEdge.SouthEast;
			else if(source > destination)
				return  TypeEdge.NorthEast;
			else
				return TypeEdge.NoDiagonal;
		}
		else
			return TypeEdge.NoDiagonal;
	}
	
	public static boolean isDiagonal(int source, int destination) 
	{
		 return ( Math.abs( source  - destination ) == DIAGONAL ) ? true : false;
	}	
	
	public boolean isInTheBorder()
	{
		return isInTheBorder;
	}
	
	public boolean isDiagonal()
	{
		return isDiagonal;
	}
	
	public TypeEdge getType()
	{
		return this.type;
	}
	
	@Override
	public String toString()
	{
		return "POINT[" + this.source + "," + this.destination + "]" ;
	}

	public boolean isLock()
	{
		return lock;
	}

	public void setLock(boolean lock)
	{
		this.lock = lock;
	}
}
