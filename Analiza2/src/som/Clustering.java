/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package som;

import java.util.ArrayList;

/**
 *
 * @author student-fiszera
 */
public abstract class Clustering 
{
	protected ArrayList<ArrayList<Double>> centers= new ArrayList<>();
	protected Data data;
	protected int numberOfDimensions;
	
	public Clustering(int numberOfCenters, Data data) 
	{
		for(int i=0; i<numberOfCenters; i++) centers.add(new ArrayList<Double>());
		this.data = data;
		this.numberOfDimensions=data.getPoints().get(0).getPoint().size();
	}
	
	protected double distanceOf(ArrayList<Double> point1, ArrayList<Double> point2) throws Exception
	{
		if(point1.size()!=point2.size()) throw new Exception("Nie mozna policzyc odleglosci dla punktow o roznej liczbie wspolrzednych");
		double result=0;
		for(int i=0; i<point1.size(); i++)
		{
			double difference = point1.get(i)-point2.get(i);
			result += difference*difference;
		}
		return Math.sqrt(result);
	}
	
	public double error() throws Exception
	{
		if(data.getPoints().get(0).getPoint().size()!=data.getPoints().get(0).getCentre().size())
		{
			throw new Exception("Centra i punkty maja rozna liczbe wspolrzednych");
		}
		double result = 0;
		for(int i=0; i<data.getPoints().size(); i++)
		{
			for(int j=0; j<data.getPoints().get(0).getPoint().size(); j++)
			{
				double difference = data.getPoints().get(i).getPoint().get(j) - data.getPoints().get(i).getCentre().get(j);
				result += difference*difference;
			}
		}
		return result/data.getPoints().size();
	}
	
	public abstract void clusterize() throws Exception;
	
	//logi
}
