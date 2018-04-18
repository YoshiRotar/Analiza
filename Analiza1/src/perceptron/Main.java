package perceptron;


public class Main {

	public static void main(String[] args) 
	{
		String path;
		for(int j=2;j<=8;j+=2)
		{
			for(int i=1;i<=5;i++)
			{
				Layer layer1 = new Layer();
				layer1.fillWithLogisticNeurons(j);
				
				Layer layer2 = new Layer();
				layer2.fillWithLinearNeurons(1);
				
				Perceptron p = new Perceptron();
				p.addLayer(layer1);
				p.addLayer(layer2);
				
				//p.setBias(false);
				p.setMomentum(0.0);
				p.setRateOfChange(0.1);
				
				try 
				{
					p.getTrainingExamplesFromFile("./data/approximation_train_2.txt", 1, 1);
					p.getTestExamplesFromFile("./data/approximation_test.txt", 1, 1);	
					path = "./data/4/wplywParametrow/r01m01/n"+j+"/przebieg"+i+".csv";
					p.setLogPath(path);
					p.initializePerceptron();
					p.learn(2500);
					
					p.resetExamples();
					p.getTrainingExamplesFromFile("./data/approximation_train_2.txt", 1, 1);
					p.getTestExamplesFromFile("./data/approximation_test.txt", 1, 1);
					path = "./data/4/wplywParametrow/r01m01/n"+j+"/wyniki"+i+".csv";
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
