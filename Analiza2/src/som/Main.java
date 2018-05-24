/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package som;

import java.util.ArrayList;

/**
 *
 * @author student
 */
public class Main
{

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception
    {
    	
    	//ArrayList<CenterWithNeighbours> graph = Kohonen.createGridGraph(3, 6);
    	System.out.println("Start");
    	
        Data d = new Data();
        d.loadFromTextFile("./data/inputs.txt",2);
        
        for(int i=1; i<=5; i++)
        {
        	//ArrayList<CenterWithNeighbours> graph = Kohonen.createGridGraph(3, 10);
        	//NeuralGas k = new NeuralGas(30, d, 0.9, 4, 30);
        	//Kohonen k = new Kohonen(d, 0.9, 30, 1, graph);
        	KMeans k = new KMeans(30, d, true);
        	k.setErrorLogPath("./results/3/k30/forgy/errors"+i+".txt");
        	k.setCentersLogPath("./results/3/k30/forgy/centers"+i+".txt");
        	k.clusterize();
        }
        System.out.println("stop");
    	
    	
    	/*
    	System.out.println("Start");
    	for(int i=1; i<=5; i++)
    	{
    		ImageCompressor c = new ImageCompressor("./data/lena.bmp", 4, 50, "./results/5/4x4/50/errors"+i+".txt");
        	c.compress();
        	c.saveToFile("./results/5/4x4/50/lena"+i+".bmp");
        	
    	}
    	System.out.println("Stop");
    	*/
    }
}
