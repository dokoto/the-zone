package engine.logic.model;

import java.awt.Color;
import java.util.UUID;

public class User
{
	private UUID id;
	private String name;
	private Color color;
	
	public User(String name, Color color)
	{
		this.id = UUID.randomUUID();
		this.setName(name);
		this.setColor(color);
	}

	public UUID getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}
	
	@Override
	public String toString()
	{
		return this.name + "[" + this.color.toString() + "," + this.id + "]";
	}
	
}	
