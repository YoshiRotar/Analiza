package som;

import java.util.ArrayList;

public class Sample 
{
	private final ArrayList<Double> point;
	private ArrayList<Double> centre;
	
	public Sample(ArrayList<Double> point) 
	{
		this.point = point;
	}

	public ArrayList<Double> getCentre() 
	{
		return centre;
	}
	
	public void setCentre(ArrayList<Double> centre) 
	{
		this.centre = centre;
	}
	
	public ArrayList<Double> getPoint() 
	{
		return point;
	}
	
	
}
