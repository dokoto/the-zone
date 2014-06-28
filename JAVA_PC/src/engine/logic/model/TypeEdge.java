package engine.logic.model;

public enum TypeEdge
{
	north(0),
	east(1),
	south(2),
	west(4),
	NorthEast(5),
	SouthEast(6),
	NoDiagonal(7);
	
	private int value;
	
	private TypeEdge(int value)
	{
		this.value = value;
	}
	
	public int getValue()
	{
		return this.value;
	}
	
}
