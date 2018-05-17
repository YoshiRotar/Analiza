package som;

public class CenterWithTiredom extends Center
{
	private double tiredom = 1.0;
	
	//mozna ewentualnie to potem sparametryzowac
	private static final double minTiredom = 0.75;

	
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
	
}
