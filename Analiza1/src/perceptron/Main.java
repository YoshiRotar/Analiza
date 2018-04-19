package perceptron;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) 
	{
		String path;
		//for(int j=4;j<=16;j+=4)
		//{
			for(int i=1;i<=5;i++)
			{
				Layer layer1 = new Layer();
				layer1.fillWithLogisticNeurons(4);
				
				Layer layer2 = new Layer();
				layer2.fillWithLinearNeurons(1);
				
				Perceptron p = new Perceptron();
				p.addLayer(layer1);
				p.addLayer(layer2);
				
				//p.setBias(false);
				p.setMomentum(0.5);
				p.setRateOfChange(0.5);
				
				try 
				{
					p.getTrainingExamplesFromFile("./data/approximation_train_2.txt", 1, 1);
					p.getTestExamplesFromFile("./data/approximation_test.txt", 1, 1);	
					//p.formatExampleOutputs();
					path = "./data/4/wplywParametrow/r05m05/przebieg"+i+".csv";
					p.setLogPath(path);
					//p.ignoreInputs(new ArrayList<Integer>(Arrays.asList(1,3)));
					p.initializePerceptron();
					p.learn(2500);
					
					p.resetExamples();
					p.getTrainingExamplesFromFile("./data/approximation_train_2.txt", 1, 1);
					p.getTestExamplesFromFile("./data/approximation_test.txt", 1, 1);	
					//p.formatExampleOutputs();
					path = "./data/4/wplywParametrow/r05m05/wyniki"+i+".csv";
					//p.ignoreInputs(new ArrayList<Integer>(Arrays.asList(1,3)));
					p.setLogPath(path);
					p.sendOutputToLog();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		//}
		System.out.println("koniec");
	}

}
