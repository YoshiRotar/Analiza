package som;

import java.util.ArrayList;
import java.util.Collections;

public class Kohonen extends SelfOrganizingMap
{

	private int levelOfNeighbourhood = 1;
	private ArrayList<CenterWithNeighbours> centers;
	
	public ArrayList<CenterWithNeighbours> getCenters() 
	{
		return centers;
	}

	public Kohonen(Data data, double learningRate, int numberOfIterations, int levelOfNeighbourhood, ArrayList<CenterWithNeighbours> centersGraph) 
	{
		super(data, learningRate, numberOfIterations);
		centers = centersGraph;
		if(levelOfNeighbourhood==1 && levelOfNeighbourhood==2) this.levelOfNeighbourhood = levelOfNeighbourhood;
		ArrayList<Double> minimums = new ArrayList<Double>();
		ArrayList<Double> maximums = new ArrayList<Double>();
		findCenterRange(minimums, maximums);
		for(int i=0; i<centers.size(); i++) 
		{
			centers.get(i).getCoordinates().clear();
			for(int j=0; j<numberOfDimensions; j++)
			{
				centers.get(i).getCoordinates().add(maximums.get(j)-minimums.get(j)*Math.random()+minimums.get(j));
			}
		}
	}
	
	public static ArrayList<CenterWithNeighbours> createGridGraph(int x, int y) throws Exception
	{
		ArrayList<CenterWithNeighbours> result = new  ArrayList<>();
		if(x<=0 || y<=0) throw new Exception("Podaj dodatnie wartosci");
		for(int i=0; i<x*y; i++)
		{
			result.add(new CenterWithNeighbours());
		}
		for(int i=0; i<x*y; i++)
		{
			//first level
			if(i-x>=0) result.get(i).getFirstLevelNeighbours().add(result.get(i-x));
			if(i+x<result.size()) result.get(i).getFirstLevelNeighbours().add(result.get(i+x));
			if((i+1)%x!=0) result.get(i).getFirstLevelNeighbours().add(result.get(i+1));
			if((i+1)%x!=1) result.get(i).getFirstLevelNeighbours().add(result.get(i-1));
			
			//second level
			if(i-x>=0 && (i+1)%x!=0) result.get(i).getSecondLevelNeighbours().add(result.get((i-x)+1));
			if(i+x<result.size() && (i+1)%x!=0) result.get(i).getSecondLevelNeighbours().add(result.get((i+x)+1));
			if(i-x>=0 && (i+1)%x!=1) result.get(i).getSecondLevelNeighbours().add(result.get((i-x)-1));
			if(i+x<result.size() && (i+1)%x!=1) result.get(i).getSecondLevelNeighbours().add(result.get((i+x)-1));
		}
		return result;
	}

	private void moveOneCenter(CenterWithTiredom center, int level, Sample sample)
	{
		for(int k=0; k<numberOfDimensions; k++)
		{
			double increment = learningRate*Math.exp(-level)*(sample.getPoint().get(k)-center.getCoordinates().get(k));
			center.getCoordinates().set(k, center.getCoordinates().get(k)+increment);
			if(level==0) center.tireTheWinner();
			else center.tireTheLoser(centers.size());
		}
	}
	
	private void moveCenters() throws Exception
	{
		for(Sample sample : data.getPoints())
		{
			for(CenterWithTiredom center : centers)
			{
				center.setDistance(distanceOf(sample.getPoint(), center.getCoordinates()));
			}
			CenterWithNeighbours winner = Collections.min(centers);
			sample.setCenter(winner);
			if(winner.getTiredom()>=CenterWithTiredom.minTiredom) moveOneCenter(winner, 0, sample);
			for(CenterWithNeighbours center : winner.getFirstLevelNeighbours()) moveOneCenter(center, 1, sample);
			if(levelOfNeighbourhood>1)for(CenterWithNeighbours center : winner.getSecondLevelNeighbours()) moveOneCenter(center, 2, sample);
		}
	}
	
	@Override
	public void clusterize() throws Exception 
	{
		for(int i=0; i<numberOfIterations; i++) moveCenters();		
	}

}
