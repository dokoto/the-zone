package engine.logic.detection;

import java.util.HashSet;
import java.util.Set;

import Utils.Logg;
import engine.logic.model.TerritoryMap;
import engine.logic.model.User;

public class Scan
{
	public static boolean run(TerritoryMap map, User user)
	{		
		Set<Integer> connectedNodes = new HashSet<Integer>();
		if (DAGCycleDetection.cycle(map, connectedNodes))
		{
			Logg.INFO("User: " + user.toString() + " Connected Nodes: " + connectedNodes);
			map.removeEdges(connectedNodes, user);
			return true;
		}
		else
			return false;
	}
}
