/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package som;

/**
 *
 * @author student
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception
    {
    	System.out.println("Start");
        Data d = new Data("./data/inputs.txt",2);
        NeuralGas k = new NeuralGas(6, d, 0.1, 3, 1000);
        k.clusterize();
    	System.out.println("Stop");
    }
    
}