package som;

import java.util.ArrayList;
import java.util.Collections;

public abstract class SelfOrganizingMap extends Clustering
{
	protected double learningRate;
	protected int numberOfIterations;
	
	//Nie wiem czy ten konstruktor nie bedzie musial byc inny dla gazu i kogonnena,
	//najwyzej sie go podzieli na metody i zaimplementuje oddzielnie
	public SelfOrganizingMap(int numberOfCenters, Data data, double learningRate, int numberOfIterations) 
	{
		super(numberOfCenters, data);
		this.learningRate=learningRate;
		this.numberOfIterations=numberOfIterations;
		
	}
	
	protected void findCenterRange(ArrayList<Double> minimums, ArrayList<Double> maximums)
	{
		for(int i=0; i<numberOfDimensions; i++)
		{
			//Wektor wspolrzednych np samych x, samych y itd.
			ArrayList<Double> coordsOfGivenDimension = new ArrayList<Double>();
			for(int j=0; j<data.getPoints().size(); j++) coordsOfGivenDimension.add(data.getPoints().get(j).getPoint().get(i));
			minimums.add(Collections.min(coordsOfGivenDimension));
			maximums.add(Collections.max(coordsOfGivenDimension));
		}
	}

}
