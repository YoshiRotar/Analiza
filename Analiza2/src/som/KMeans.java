package som;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class KMeans extends Clustering
{

	boolean forgy;
	
	public KMeans(int numberOfCenters, Data data, boolean forgy) 
	{
		super(numberOfCenters, data);
		this.forgy = forgy;
	}
	
	private void forgy()
	{
		for(ArrayList<Double> centre : centers)
		{
			int randomNum = ThreadLocalRandom.current().nextInt(0, data.getPoints().size());
			centre.addAll(data.getPoints().get(randomNum).getPoint());
		}
	}
	
	private void randomPartition()
	{
		for(Sample sample : data.getPoints())
		{
			int randomNum = ThreadLocalRandom.current().nextInt(0, centers.size());
			sample.setCentre(centers.get(randomNum));
		}
	}
	
	public void init()
	{
		if(forgy) forgy();
		else
		{
			randomPartition();
			moveCenters();
		}
	}
	
	private boolean findMemberships() throws Exception
	{
		boolean changes = false;
		for(Sample sample : data.getPoints())
		{
			ArrayList<Double> membership = new ArrayList<>();
			double minDistance = -1;
			for(ArrayList<Double> centre : centers)
			{
				double distance = distanceOf(centre, sample.getPoint());
				if(minDistance < 0 || minDistance>distance)
				{
					minDistance=distance;
					membership=centre;
				}
			}
			if(sample.getCentre()!=membership) changes = true;
			sample.setCentre(membership);
		}
		return changes;
	}
	
	private void moveCenters()
	{
		for(ArrayList<Double> centre : centers)
		{
			int numberOfSamples = 0;
			ArrayList<Double> average = new ArrayList<>();
			for(int i=0; i<numberOfDimensions; i++) average.add(0.0);
			for(Sample sample : data.getPoints())
			{
				if(sample.getCentre()==centre)
				{
					numberOfSamples++;
					for(int i=0; i<numberOfDimensions; i++) average.set(i, average.get(i)+sample.getPoint().get(i));
				}
			}
			for(int i=0; i<numberOfDimensions; i++) average.set(i, average.get(i)/numberOfSamples);
			centre.clear();
			centre.addAll(average);
		}	
	}
	
	public void clusterize() throws Exception
	{
		while(findMemberships())
		{
			moveCenters();
		}
	}
}
