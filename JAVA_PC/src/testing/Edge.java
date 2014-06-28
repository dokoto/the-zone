package testing;

import java.awt.Color;
import java.awt.geom.Line2D;

public class Edge
{
	private Line2D edge;
	private Color color;
	
	public Edge(Line2D edge, Color color)
	{
		this.setEdge(edge);
		this.setColor(color);
	}

	public Line2D getEdge()
	{
		return edge;
	}

	public void setEdge(Line2D edge)
	{
		this.edge = edge;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}
}
