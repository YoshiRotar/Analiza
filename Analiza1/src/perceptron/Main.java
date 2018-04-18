package perceptron;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) 
	{
		String path;
		for(int j=4;j<=16;j+=4)
		{
			for(int i=1;i<=5;i++)
			{
				Layer layer1 = new Layer();
				layer1.fillWithLogisticNeurons(j);
				
				Layer layer2 = new Layer();
				layer2.fillWithLinearNeurons(3);
				
				Perceptron p = new Perceptron();
				p.addLayer(layer1);
				p.addLayer(layer2);
				
				//p.setBias(false);
				p.setMomentum(0.1);
				p.setRateOfChange(0.1);
				
				try 
				{
					p.getTrainingExamplesFromFile("./data/classification_train.txt", 4, 1);
					p.getTestExamplesFromFile("./data/classification_test.txt", 4, 1);	
					p.formatExampleOutputs();
					path = "./data/5/24/n"+j+"/przebieg"+i+".csv";
					p.setLogPath(path);
					p.ignoreInputs(new ArrayList<Integer>(Arrays.asList(1,3)));
					p.initializePerceptron();
					p.learn(2500);
					
					p.resetExamples();
					p.getTrainingExamplesFromFile("./data/classification_train.txt", 4, 1);
					p.getTestExamplesFromFile("./data/classification_test.txt", 4, 1);	
					p.formatExampleOutputs();
					path = "./data/5/24/n"+j+"/wyniki"+i+".csv";
					p.ignoreInputs(new ArrayList<Integer>(Arrays.asList(1,3)));
					p.setLogPath(path);
					p.sendOutputToLog();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}
		System.out.println("koniec");
	}

}
