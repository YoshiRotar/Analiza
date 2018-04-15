package perceptron;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) 
	{

		Layer layer1 = new Layer();
		layer1.fillWithLogisticNeurons(3);
		Layer layer2 = new Layer();
		layer2.fillWithLogisticNeurons(4);
		Perceptron p = new Perceptron();
		p.addLayer(layer1);
		p.addLayer(layer2);
		p.setBias(false);
		p.setMomentum(0.1);
		p.setRateOfChange(0.5);
		try 
		{
			p.getTrainingExamplesFromFile("./data/data.txt", 4, 4);
			//p.getTestExamplesFromFile("./data/data.txt", 4, 4);
			p.setLogPath("./data/3/r05m01/nobias/n3/przebieg5.csv");
			p.initializePerceptron();
			p.learn(2500);
			ArrayList<Double> inputs = new ArrayList<Double>(Arrays.asList(0.0, 0.0, 1.0, 0.0));
			ArrayList<Double> outputs = p.process(inputs);
			System.out.println(outputs);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}

}
