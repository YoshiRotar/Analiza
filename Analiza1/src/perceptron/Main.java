package perceptron;


public class Main {

	public static void main(String[] args) 
	{
		String path;
		for(int j=1;j<=3;j++)
		{
			for(int i=1;i<=5;i++)
			{
				Layer layer1 = new Layer();
				layer1.fillWithLogisticNeurons(j);
				
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
					path = "./data/3/r05m01/nobias/n"+j+"/przebieg"+i+".csv";
					p.setLogPath(path);
					p.initializePerceptron();
					p.learn(2500);
					
					p.resetExamples();
					p.getTrainingExamplesFromFile("./data/data.txt", 4, 4);
					path = "./data/3/r05m01/nobias/n"+j+"/wyniki"+i+".csv";
					p.setLogPath(path);
					p.sendOutputToLog();
					
					
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}
	}

}
