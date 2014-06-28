package testing;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import Utils.Logg;
import engine.logic.detection.DAGCycleDetection;
import engine.logic.detection.Scan;
import engine.logic.model.TerritoryMap;
import engine.logic.model.User;


public class TestDetector
{
	private final static int ROWS = 5, COLUMNS = 5, NUM_OF_USERS = 2, WIDTH = 1024, HEIHT = 880;
	private final static int NUM_OF_NODES = ROWS * COLUMNS;		
	private Canvas canvas;
	private static class interactiveStatus
	{
		public static boolean ON = true, OFF = false;
	}
	private final static String TITLE = "THE ZONE [DESKTOP_UI_v] BETA 0.01";
	
	
	
	public TestDetector()	
	{
		Logg.INFO("Creating a table of ROWS: " + ROWS + "|COLUMNS: " + COLUMNS + " with : " + NUM_OF_NODES + " nodes");		
	}
	
	public void runSimulationONE()
	{						
		this.canvas = new Canvas(TITLE, ROWS, COLUMNS, WIDTH, HEIHT, NUM_OF_USERS, interactiveStatus.OFF);
		canvas.showCanvas();
		SIM1();
	}
	
	public void runSimulationTWO()
	{					
		Logg.INFO("Simulation of free selection for serveral users");
		this.canvas = new Canvas(TITLE, ROWS, COLUMNS, WIDTH, HEIHT, NUM_OF_USERS, interactiveStatus.ON);
		canvas.showCanvas();
	}
	
	/*
	private TerritoryMap SIM3()
	{

		Logg.INFO("Creating complex shape");
		
		TerritoryMap map = TerritoryMap.createNodes(ROWS, COLUMNS);
		map.add(1, 2, userIdONE);
		map.add(2, 6, userIdONE);
		map.add(6, 10, userIdONE);
		map.add(10, 11, userIdONE);
		map.add(11, 15, userIdONE);
		map.add(15, 14, userIdONE);
		map.add(14, 13, userIdONE);
		map.add(13, 12, userIdONE);
		map.add(12, 8, userIdONE);
		map.add(8, 9, userIdONE);
		map.add(9, 5, userIdONE);
		map.add(5, 1, userIdONE);
		
		Logg.INFO("SIM1: Map: \n" + map.getEdges().toString());
		
		return map;
		
		return null;
	}*/
	
	private void SIM1()
	{
		
		Logg.INFO("Creating two triangles sharing the same diagonal, Two players");				
		
		User users[] = canvas.getUsers();
		TerritoryMap map = canvas.getMap();
		
		canvas.drawEdgeManual(1, 2, users[0]); 
		Scan.run(map, users[0]);
		
		canvas.drawEdgeManual(1, 6, users[1]);
		Scan.run(map, users[1]);
				
		canvas.drawEdgeManual(2, 6, users[0]);
		Scan.run(map, users[0]);
		
		canvas.drawEdgeManual(6, 5, users[1]);
		Scan.run(map, users[1]);
		
		canvas.drawEdgeManual(6, 1, users[0]);
		Scan.run(map, users[0]);
								
		canvas.drawEdgeManual(5, 1, users[1]);
		Scan.run(map, users[1]);					
				
	}	

}
