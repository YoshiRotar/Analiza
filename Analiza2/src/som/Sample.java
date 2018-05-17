package som;

import java.util.ArrayList;

public class Sample 
{
	private final ArrayList<Double> point;
	private Center center;
	
	public Sample(ArrayList<Double> point) 
	{
		this.point = point;
	}

	public Center getCenter() 
	{
		return center;
	}
	
	public void setCenter(Center center) 
	{
		this.center = center;
	}
	
	public ArrayList<Double> getPoint() 
	{
		return point;
	}
	
	
}
