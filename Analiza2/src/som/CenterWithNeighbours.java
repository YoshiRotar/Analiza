package som;

import java.util.ArrayList;

public class CenterWithNeighbours extends CenterWithTiredom
{
	ArrayList<CenterWithNeighbours> firstLevelNeighbours = new ArrayList<CenterWithNeighbours>();
	ArrayList<CenterWithNeighbours> secondLevelNeighbours = new ArrayList<CenterWithNeighbours>();
	
	public ArrayList<CenterWithNeighbours> getFirstLevelNeighbours() 
	{
		return firstLevelNeighbours;
	}
	public ArrayList<CenterWithNeighbours> getSecondLevelNeighbours() 
	{
		return secondLevelNeighbours;
	}
}
