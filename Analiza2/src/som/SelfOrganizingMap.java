package som;

import java.util.ArrayList;
import java.util.Collections;

public abstract class SelfOrganizingMap extends Clustering
{
	double learningRate;
	
	//Nie wiem czy ten konstruktor nie bedzie musial byc inny dla gazu i kogonnena,
	//najwyzej sie go podzieli na metody i zaimplementuje oddzielnie
	public SelfOrganizingMap(int numberOfCenters, Data data, double learningRate) 
	{
		super(numberOfCenters, data);
		this.learningRate=learningRate;
		ArrayList<Double> minimums = new ArrayList<Double>();
		ArrayList<Double> maximums = new ArrayList<Double>();
		for(int i=0; i<numberOfDimensions; i++)
		{
			//Wektor wspolrzednych np samych x, samych y itd.
			ArrayList<Double> coordsOfGivenDimension = new ArrayList<Double>();
			for(int j=0; j<data.getPoints().size(); j++) coordsOfGivenDimension.add(data.getPoints().get(j).getPoint().get(i));
			minimums.add(Collections.min(coordsOfGivenDimension));
			maximums.add(Collections.max(coordsOfGivenDimension));
		}
		for(int i=0; i<numberOfCenters; i++) 
		{
			Center center =  new CenterWithTiredom();
			for(int j=0; j<numberOfDimensions; j++) 
			{
				center.getCoordinates().add((maximums.get(j)-minimums.get(j))*Math.random()+minimums.get(j));
			}
			centers.add(center);
		}
	}

}
