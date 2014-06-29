package testing;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import engine.logic.model.TerritoryMap;
import engine.logic.model.User;

@SuppressWarnings("serial")
public class Canvas extends JFrame
{
	private int screenWidth, screenHeight;
	private ControlPanel controlPanel;
	private MapSurface surfaceMap;
	
	
	public Canvas(String title, int rows, int columns, int screenWidth, int screenHeight, int numOfUsers, boolean interActive)
	{
		setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                
        
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);
        
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.surfaceMap = new MapSurface(rows, columns, 800, 800, interActive, numOfUsers);
        this.controlPanel = new ControlPanel(this.surfaceMap.getUsers());
        this.surfaceMap.setControlPanel(controlPanel);
        
	}
	
	
	public User[] getUsers()
	{
		return surfaceMap.getUsers();
	}
	
	public TerritoryMap getMap()
	{
		return this.surfaceMap.getMap();
	}
	
	public void drawEdgeManual(int source, int destination, User user)
	{
		surfaceMap.drawEdge(source, destination, user.getColor());
	}
	
	public void showCanvas()
	{	
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		split.setLeftComponent(surfaceMap);
		split.setRightComponent(controlPanel);	
		split.setDividerLocation(850);
		add(split);
		setResizable(false);		
		setVisible(true);
	}
	
}
