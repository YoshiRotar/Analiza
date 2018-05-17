package som;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class KMeans extends Clustering
{

	private ArrayList<Center> centers= new ArrayList<>();
	boolean forgy;
	
	public KMeans(int numberOfCenters, Data data, boolean forgy) 
	{
		super(numberOfCenters, data);
		for(int i=0; i<numberOfCenters; i++) centers.add(new Center());
		this.forgy = forgy;
	}
	
	private void forgy()
	{
		for(Center centre : centers)
		{
			int randomNum = ThreadLocalRandom.current().nextInt(0, data.getPoints().size());
			centre.getCoordinates().addAll(data.getPoints().get(randomNum).getPoint());
		}
	}
	
	private void randomPartition()
	{
		for(Sample sample : data.getPoints())
		{
			int randomNum = ThreadLocalRandom.current().nextInt(0, centers.size());
			sample.setCenter(centers.get(randomNum));
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
			Center membership = new Center();
			double minDistance = -1;
			for(Center center : centers)
			{
				double distance = distanceOf(center.getCoordinates(), sample.getPoint());
				if(minDistance < 0 || minDistance>distance)
				{
					minDistance=distance;
					membership=center;
				}
			}
			if(sample.getCenter()!=membership) changes = true;
			sample.setCenter(membership);
		}
		return changes;
	}
	
	private void moveCenters()
	{
		for(Center center : centers)
		{
			int numberOfSamples = 0;
			ArrayList<Double> average = new ArrayList<>();
			for(int i=0; i<numberOfDimensions; i++) average.add(0.0);
			for(Sample sample : data.getPoints())
			{
				if(sample.getCenter()==center)
				{
					numberOfSamples++;
					for(int i=0; i<numberOfDimensions; i++) average.set(i, average.get(i)+sample.getPoint().get(i));
				}
			}
			for(int i=0; i<numberOfDimensions; i++) average.set(i, average.get(i)/numberOfSamples);
			center.getCoordinates().clear();
			center.getCoordinates().addAll(average);
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
