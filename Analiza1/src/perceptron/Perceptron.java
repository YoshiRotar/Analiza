package perceptron;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Perceptron 
{
	private ArrayList<Double> initialInputs = new ArrayList<Double>();
	private ArrayList<Layer> layers = new ArrayList<Layer>();
	private ArrayList<TrainingExample> examples = new ArrayList<TrainingExample>();
	
	public void initializePerceptron() throws Exception
	{
		//+1 dlatego, ze bias
		if(layers.size()<1) throw new Exception();
		layers.get(0).initializeNeurons(initialInputs.size()+1);
		for(int i=1; i<layers.size(); i++)
		{
			layers.get(i).initializeNeurons(layers.get(i-1).getNumberOfNeurons()+1);
		}
	}
	
	public ArrayList<Double> process() throws Exception
	{
		if(layers.size()<1) throw new Exception();
		ArrayList<Double> inputs = initialInputs;
		for(int i=0; i<layers.size(); i++)
		{
			//bias
			inputs.add(1.0);
			layers.get(i).setInputs(inputs);
			layers.get(i).process();
			inputs = layers.get(i).getOutputs(); 
		}
		return inputs;
	}
	
	public void getExamplesFromFile(String path, int inputs, int outputs) throws Exception
	{
		File file = new File(path);
		try
		{
			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) 
			{
				TrainingExample example = new TrainingExample();
				for(int i=0; i<inputs; i++)
				{
					example.getInputs().add(Double.valueOf(scanner.next()));
				}
				for(int i=0; i<outputs; i++)
				{
					example.getInputs().add(Double.valueOf(scanner.next()));
				}
				examples.add(example);
			}
			scanner.close();
		}
		catch(Exception e)
		{
			throw new Exception();
		}
	}

}
