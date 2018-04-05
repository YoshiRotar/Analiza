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
	private double rateOfChange; //chyba powinien byc zwiazany z siecia
	
	public void initializePerceptron() throws Exception
	{
		//+1 dlatego, ze bias
		if(layers.size()<1) throw new Exception();
		layers.get(0).initializeNeurons(numberOfInitialInputs+1);
		for(int i=1; i<layers.size(); i++)
		{
			layers.get(i).initializeNeurons(layers.get(i-1).getNumberOfNeurons()+1);
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
		try
		{
			ArrayList<Double> outputs = process(initialInputs);
			ArrayList<Double> errors = expectedOutputs;
			if(outputs.size()!=expectedOutputs.size()) throw new Exception();
			for(int i=0; i<outputs.size(); i++)
			{
				errors.set(i, errors.get(i) - expectedOutputs.get(i));
			}
			for(int i=layers.size()-1; i>=0; i++)
			{
				errors = layers.get(i).backPropagation(errors, rateOfChange);
			}
		}
		catch(Exception e)
		{
			throw new Exception();
		}
	}
	
	public void age() throws Exception
	{
		for(int i=0; i<examples.size(); i++)
		{
			try
			{
				cycle(examples.get(i).getInputs(), examples.get(i).getOutputs());
			}
			catch (Exception e)
			{
				throw new Exception();
			}
		}
	}
	
	public void learn(int numberOfAges) throws Exception
	{
		for(int i=0; i<numberOfAges; i++)
		{
			Collections.shuffle(examples);
			try
			{
				age();
			}
			catch(Exception e)
			{
				throw new Exception();
			}
			
		}
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
