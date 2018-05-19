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
    	ArrayList<CenterWithNeighbours> graph = Kohonen.createGridGraph(3, 6);
    	System.out.println("Start");
        Data d = new Data("./data/inputs.txt",2);
        Kohonen k = new Kohonen(d, 0.1, 30, 2, graph);
        k.clusterize();
    	System.out.println("Stop");
    	for(Center center : k.getCenters())
    	{
    		for(Double coord : center.getCoordinates())
    			System.out.print(coord + ", ");
    		System.out.println();
    	}
    }
}
