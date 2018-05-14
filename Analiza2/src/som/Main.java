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
        KMeans k = new KMeans(6, d, true);
        k.init();
        k.clusterize();
    	System.out.println("Stop");
    }
    
}
