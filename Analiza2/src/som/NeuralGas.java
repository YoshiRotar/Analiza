package som;

import java.util.ArrayList;
import java.util.Collections;

public class NeuralGas extends SelfOrganizingMap
{
	private int numberOfCentersToMove;
	private ArrayList<CenterWithTiredom> centers= new ArrayList<>();
	
	public ArrayList<CenterWithTiredom> getCenters() 
	{
		return centers;
	}

	public NeuralGas(int numberOfCenters, Data data, double learningRate, int numberOfCentersToMove, int  numberOfIterations) throws Exception
	{
		super(data, learningRate, numberOfIterations);
		ArrayList<Double> minimums = new ArrayList<Double>();
		ArrayList<Double> maximums = new ArrayList<Double>();
		findCenterRange(minimums, maximums);
		for(int i=0; i<numberOfCenters; i++) 
		{
			CenterWithTiredom center =  new CenterWithTiredom();
			for(int j=0; j<numberOfDimensions; j++) 
			{
				center.getCoordinates().add((maximums.get(j)-minimums.get(j))*Math.random()+minimums.get(j));
			}
			centers.add(center);
		}
		if(numberOfCentersToMove>=centers.size()) throw new Exception("Liczba centrow do przesuniecia nie moze byc wieksza od liczby wszystkich centrow");	
		this.numberOfCentersToMove = numberOfCentersToMove;
	}

	private void moveCenters() throws Exception
	{
		for(Sample sample : data.getPoints())
		{
			for(CenterWithTiredom center : centers)
			{
				center.setDistance(distanceOf(sample.getPoint(), center.getCoordinates()));
			}
			Collections.sort(centers);
			sample.setCenter(centers.get(0));
			for(int j=0; j<numberOfCentersToMove; j++)
			{
				ArrayList<Double> coordinatesToChange = centers.get(j).getCoordinates();
				if(centers.get(j).getTiredom()>=CenterWithTiredom.minTiredom)
				{
					for(int k=0; k<numberOfDimensions; k++)
					{
						coordinatesToChange.set(k, coordinatesToChange.get(k)+(learningRate*Math.exp(-j)*(sample.getPoint().get(k)-coordinatesToChange.get(k))));
					}
					
					if(j==0) centers.get(j).tireTheWinner();
					else centers.get(j).tireTheLoser(centers.size());
				}	
			}
		}
	}
	
	@Override
	public void clusterize() throws Exception 
	{
		for(int i=0; i<numberOfIterations; i++) moveCenters();
	}
	
}
