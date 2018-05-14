/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package som;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author student
 */
public class Data 
{
    private final ArrayList<Sample> points = new ArrayList<>();

    public ArrayList<Sample> getPoints() 
    {
		return points;
	}

	public Data(String path, int size) throws Exception
    {
        File file = new File(path);
        try(Scanner scanner = new Scanner(file))
        {
            scanner.useDelimiter(",|\n");
            while (scanner.hasNext()) 
            {
                ArrayList<Double> temp = new ArrayList<>();
                for(int i=0; i<size; i++)
                {
                    temp.add(Double.valueOf(scanner.next()));
                }
                points.add(new Sample(temp));
            }
            scanner.close();
        }
    }
    
    public void swietl()
    {
        for (Sample sample : points) 
        {
            System.out.println(sample.getPoint());
        }
    }
}
