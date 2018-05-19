package som;

public class CenterWithTiredom extends Center implements Comparable<CenterWithTiredom>
{
	protected double tiredom = 1.0;
	
	//nie jestem pewien czy tylko gaz ma mechanizm przemeczenia
	protected double distance;
	
	//mozna ewentualnie to potem sparametryzowac
	protected static final double minTiredom = 0.75;

	
	public double getTiredom() 
	{
		return tiredom;
	}
	
	public void setTiredom(double tiredom) 
	{
		this.tiredom = tiredom;
	}
	
	public void tireTheWinner()
	{
		this.tiredom-=minTiredom;
	}
	
	public void tireTheLoser(int numberOfCenters)
	{
		this.tiredom+=1/numberOfCenters;
	}

	public double getDistance() 
	{
		return distance;
	}

	public void setDistance(double distance) 
	{
		this.distance = distance;
	}

	@Override
	public int compareTo(CenterWithTiredom center) 
	{
		if(this.distance>center.distance)return 1;
		else if(this.distance<center.distance)return -1;
		return 0;
	}
}
