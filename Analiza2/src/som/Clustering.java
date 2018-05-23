/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package som;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author student-fiszera
 */
public abstract class Clustering 
{
	protected Data data;
	protected int numberOfDimensions;
	protected Path errorLogPath = null;
	protected Path centersLogPath = null;
	protected String stringToLog = "";
	
	public void setErrorLogPath(String path) 
	{
		this.errorLogPath = Paths.get(path);
	}
	
	public void setCentersLogPath(String path) 
	{
		this.centersLogPath = Paths.get(path);
	}
	
	public Clustering(Data data) 
	{
		this.data = data;
		this.numberOfDimensions=data.getPoints().get(0).getPoint().size();
	}
	
	protected void clearLog(Path logPath)
	{
		FileWriter writer;
		try 
		{
			File file = logPath.getParent().toFile();
			file.mkdirs();
			writer = new FileWriter(logPath.toFile());
			writer.write("");
			writer.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	protected double distanceOf(ArrayList<Double> point1, ArrayList<Double> point2) throws Exception
	{
		if(point1.size()!=point2.size()) throw new Exception("point1.size = "+point1.size() +" point.size = "+point2.size());
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
		if(data.getPoints().get(0).getPoint().size()!=data.getPoints().get(0).getCenter().getCoordinates().size())
		{
			throw new Exception("Centra i punkty maja rozna liczbe wspolrzednych");
		}
		double result = 0;
		for(int i=0; i<data.getPoints().size(); i++)
		{
			for(int j=0; j<data.getPoints().get(0).getPoint().size(); j++)
			{
				double difference = data.getPoints().get(i).getPoint().get(j) - data.getPoints().get(i).getCenter().getCoordinates().get(j);
				result += difference*difference;
			}
		}
		//System.out.println(data.getPoints().get(0).getPoint().get(0));
		//System.out.println(data.getPoints().get(0).getCenter());
		//System.out.println(result/data.getPoints().size());
		return result/data.getPoints().size();
	}
	
	protected void log(String text, Path logPath)
	{
		if(logPath==null) return;
		File file = logPath.getParent().toFile();
		file.mkdirs();
		FileWriter writer;
		try 
		{
			writer =  new FileWriter(logPath.toFile(),true);
			writer.write(text+"\n");
			writer.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public abstract void clusterize() throws Exception;
	
	//logi
}
