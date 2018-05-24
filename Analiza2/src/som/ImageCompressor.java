package som;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ImageCompressor 
{
	private Data data = new Data();
	private BufferedImage img = null;
	private int gridWidth;
	private String errorLogPath;
	private int numberOfCenters;
	
	ImageCompressor(String path, int gridWidth, int numberOfCenters, String errorLogPath) throws Exception
	{
		this.gridWidth = gridWidth;
		loadFromImage(path);
		this.errorLogPath = errorLogPath;
		this.numberOfCenters = numberOfCenters;
	}
	
	private double average(ArrayList<Double> list)
	{
		double sum=0;
		for(Double d : list) sum+=d;
		return sum/list.size();
	}
	
	private Sample loadGrid(int i, int j)
	{
		ArrayList<Double> temp = new ArrayList<>();
		for(int k=i; k<i+gridWidth; k++)
		{
			for(int l=j; l<j+gridWidth; l++)
			{
				if(k<img.getHeight() && l<img.getWidth()) temp.add((double)(img.getRGB(k, l) >> 16));
				else temp.add(average(temp));
			}
		}
		return new Sample(temp);
	}
	
	private void loadFromImage(String path) throws Exception
	{
	    ArrayList<Sample> points = new ArrayList<>();
		img = ImageIO.read(new File(path));
		for(int i=0; i<img.getHeight(); i+=gridWidth)
		{
			for(int j=0; j<img.getWidth(); j+=gridWidth)
			{
				points.add(loadGrid(i, j));
			}
		}
		data.setPoints(points);
	}
	
	private void compressGrid(int i, int j, int cellIndex)
	{
		ArrayList<Double> cell = data.getPoints().get(cellIndex).getCenter().getCoordinates();
		int pixelIndex=0;
		for(int k=i; k<i+gridWidth; k++)
		{
			for(int l=j; l<j+gridWidth; l++)
			{
				int pixel =(cell.get(pixelIndex).intValue() & 0xff);
				if(k<img.getHeight() && l<img.getWidth()) img.setRGB(k,l, new Color(pixel,pixel,pixel).getRGB());
			}
		}
	}
	
	
	public void saveToFile(String path) throws IOException
	{
		File file = new File(path);
		ImageIO.write(img, "bmp", file);
	}
	
	public void compress() throws Exception
	{
		if(data.getPoints().size()==0)throw new Exception("Nalezy wczytac plik bmp przed rozpoaczeciem kompresji");
		KMeans clustering= new KMeans(numberOfCenters, data, true);
		clustering.setErrorLogPath(errorLogPath);

		clustering.clusterize();

		int k=0;
		for(int i=0; i<img.getHeight(); i+=gridWidth)
		{
			for(int j=0; j<img.getWidth(); j+=gridWidth)
			{
				compressGrid(i,j,k);
				k++;
			}
		}
		
	}
	
	
}
