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
			int winnerIndex=-1;
			for(int j=0, centersMoved=0; j<centers.size(); j++)
			{
				if(centers.get(j).getTiredom()>=CenterWithTiredom.minTiredom && centersMoved<numberOfCentersToMove)
				{
					if(centersMoved==0) 
					{
						winnerIndex = j;

						sample.setCenter(centers.get(winnerIndex));
						centers.get(winnerIndex).tireTheWinner();
					}
					centersMoved++;
					for(int k=0; k<numberOfDimensions; k++)
					{
						double increment = learningRate*Math.exp(-centersMoved)*(sample.getPoint().get(k)-centers.get(j).getCoordinates().get(k));
						centers.get(j).getCoordinates().set(k, centers.get(j).getCoordinates().get(k)+increment);
					}
				}	
				if(j!=winnerIndex) centers.get(j).tireTheLoser(centers.size());
			}
		}
	}
	
	@Override
	public void clusterize() throws Exception 
	{
		if(errorLogPath!=null) clearLog(errorLogPath);
		if(centersLogPath!=null) clearLog(centersLogPath);
		for(int i=0; i<numberOfIterations; i++) 
		{
			moveCenters();
			log(Double.toString(error()), errorLogPath);
		}
		String coords = "";
		for(Center center : centers)
		{
			for(int i=0; i<center.getCoordinates().size(); i++)
			{
				coords+=center.getCoordinates().get(i);
				if(i!=(center.getCoordinates().size()-1)) coords+=", ";
			}
			coords+="\n";
		}
		log(coords, centersLogPath);
	}
	
}
