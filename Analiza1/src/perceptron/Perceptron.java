package perceptron;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Perceptron 
{
	private int numberOfInitialInputs;
	private ArrayList<Layer> layers = new ArrayList<Layer>();
	private ArrayList<TrainingExample> examples = new ArrayList<TrainingExample>();
	private double rateOfChange = 0.1;
	private boolean bias = true;
	

	public void setBias(boolean bias) 
	{
		this.bias = bias;
	}
	
	public void setNumberOfInitialInputs(int inputs)
	{
		numberOfInitialInputs = inputs;
	}
	
	public void addLayer(Layer layer)
	{
		layers.add(layer);
	}
	
	public void initializePerceptron() throws Exception
	{
		//+1 dlatego, ze bias
		if(layers.size()<1) throw new Exception();
		int numberOfNeurons = numberOfInitialInputs;
		if(bias) numberOfNeurons++;
		layers.get(0).initializeNeurons(numberOfInitialInputs+1);
		for(int i=1; i<layers.size(); i++)
		{
			numberOfNeurons = layers.get(i-1).getNumberOfNeurons();
			if(bias) numberOfNeurons++;
			layers.get(i).initializeNeurons(numberOfNeurons);
		}
	}
	
	public ArrayList<Double> process(ArrayList<Double> initialInputs) throws Exception
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
	
	public void backPropagation(ArrayList<Double> errors, double rateOfChange) throws Exception
	{
		System.out.println(errors);
		if(layers.size()<1) throw new Exception();
		try
		{
			ArrayList<Double> tempErrors = layers.get(layers.size()-1).backPropagation(errors, rateOfChange);
			for(int i=layers.size()-2; i>=0; i++)
			{
				tempErrors = layers.get(i).backPropagation(tempErrors, rateOfChange);
			}
		}
		catch(Exception e)
		{
			throw new Exception();
		}
	}
	
	public void cycle(ArrayList<Double> initialInputs, ArrayList<Double> expectedOutputs) throws Exception
	{
		ArrayList<Double> inputs = new ArrayList<Double>(initialInputs);
		ArrayList<Double> outputs = process(inputs);
		ArrayList<Double> errors = new ArrayList<Double>(expectedOutputs);
		if(outputs.size()!=expectedOutputs.size()) throw new Exception();
		for(int i=0; i<outputs.size(); i++)
		{
			errors.set(i, outputs.get(i) - expectedOutputs.get(i));
		}
		for(int i=layers.size()-1; i>=0; i--)
		{
			errors = layers.get(i).backPropagation(errors, rateOfChange);
		}
	}
	
	public void age() throws Exception
	{
		for(int i=0; i<examples.size(); i++)
		{
			cycle(examples.get(i).getInputs(), examples.get(i).getOutputs());
		}
	}
	
	public void learn(int numberOfAges) throws Exception
	{
		for(int i=0; i<numberOfAges; i++)
		{
			Collections.shuffle(examples);
			age();
		}
	}
	
	public void getExamplesFromFile(String path, int inputs, int outputs) throws Exception
	{
		numberOfInitialInputs = inputs;
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
					example.getOutputs().add(Double.valueOf(scanner.next()));
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
