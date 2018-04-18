package perceptron;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Perceptron 
{
	private int numberOfInitialInputs;
	private ArrayList<Layer> layers = new ArrayList<Layer>();
	private ArrayList<TrainingExample> examples = new ArrayList<TrainingExample>();
	private ArrayList<TrainingExample> testExamples = new ArrayList<TrainingExample>();
	private double rateOfChange = 0.1;
	private double momentum = 0.0;
	private double errorMSE = 0.0;
	private Path logPath = null;
	private boolean bias = true;
	
	public void setRateOfChange(double rateOfChange) 
	{
		this.rateOfChange = rateOfChange;
	}
	
	public void setLogPath(String path) 
	{
		this.logPath = Paths.get(path);
	}

	public void setMomentum(double momentum) 
	{
		this.momentum = momentum;
	}
	
	public void setNumberOfInitialInputs(int inputs)
	{
		numberOfInitialInputs = inputs;
	}
	
	public void addLayer(Layer layer)
	{
		layers.add(layer);
	}
	
	public void resetExamples()
	{
		examples = new ArrayList<TrainingExample>();
	}
	
	// O S T R O Z N I E !
	public void setBias(boolean bias) 
	{
		this.bias = bias;
	}
	
	public void ignoreInputs(ArrayList<Integer> toIgnore)
	{
		int k=0;
		for(int i=numberOfInitialInputs-1; i>=0; i--)
		{
			for(int j=0; j<toIgnore.size(); j++)
			{
				if(i==(toIgnore.get(j)-1))
				{
					k++;
					for(int l=examples.size()-1; l>=0; l--)
					{
						examples.get(l).getInputs().remove(i);
					}
					for(int l=testExamples.size()-1; l>=0; l--)
					{
						testExamples.get(l).getInputs().remove(i);
					}
				}
			}
		}
		numberOfInitialInputs-=k;
	}
	
	public void initializePerceptron() throws Exception
	{
		//+1 dlatego, ze bias
		if(layers.size()<1) throw new Exception();
		int numberOfWeights = numberOfInitialInputs;
		if(bias) numberOfWeights++;
		layers.get(0).initializeNeurons(numberOfWeights);
		for(int i=1; i<layers.size(); i++)
		{
			numberOfWeights = layers.get(i-1).getNumberOfNeurons();
			if(bias) numberOfWeights++;
			layers.get(i).initializeNeurons(numberOfWeights);
		}
	}
	
	public ArrayList<Double> process(ArrayList<Double> initialInputs) throws Exception
	{
		if(layers.size()<1) throw new Exception();
		ArrayList<Double> inputs = initialInputs;
		for(int i=0; i<layers.size(); i++)
		{
			//bias
			if(bias) inputs.add(1.0);
			layers.get(i).setInputs(inputs);
			layers.get(i).process();
			inputs = layers.get(i).getOutputs();
		}
		return inputs;
	}
	
	/*
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
	*/
	
	public void addToMSE(ArrayList<Double> outputs, ArrayList<Double> expectedOutputs)
	{
		for(int i=0; i<outputs.size(); i++)
		{
			double difference = outputs.get(i)-expectedOutputs.get(i);
			errorMSE+=difference*difference;
		}
	}
	
	public void cycle(ArrayList<Double> initialInputs, ArrayList<Double> expectedOutputs) throws Exception
	{
		ArrayList<Double> inputs = new ArrayList<Double>(initialInputs);
		ArrayList<Double> outputs = process(inputs);
		addToMSE(outputs, expectedOutputs);
		ArrayList<Double> errors = new ArrayList<Double>(expectedOutputs);
		if(outputs.size()!=expectedOutputs.size()) throw new Exception();
		for(int i=0; i<outputs.size(); i++)
		{
			errors.set(i, outputs.get(i) - expectedOutputs.get(i));
		}
		for(int i=layers.size()-1; i>=0; i--)
		{
			errors = layers.get(i).backPropagation(errors, rateOfChange, momentum);
		}
	}
	
	public void age() throws Exception
	{
		errorMSE=0;
		String stringToLog = new String();
		for(int i=0; i<examples.size(); i++)
		{
			cycle(examples.get(i).getInputs(), examples.get(i).getOutputs());
		}
		stringToLog += errorMSE/examples.size();
		stringToLog += ",";
		errorMSE=0;
		ArrayList<Double> testOutputs = new ArrayList<Double>();
		for(int i=0; i<testExamples.size(); i++)
		{
			testOutputs = process(testExamples.get(i).getInputs());
			addToMSE(testExamples.get(i).getOutputs(), testOutputs);
		}
		if(errorMSE!=0) stringToLog += errorMSE/testExamples.size();
		if(logPath!=null) log(stringToLog);
	}
	
	public void learn(int numberOfAges) throws Exception
	{
		if(logPath!=null) clearLog();
		for(int i=0; i<numberOfAges; i++)
		{
			Collections.shuffle(examples);
			age();
		}
	}
	
	private String makeStringToLog(TrainingExample example)
	{
		ArrayList<Double> outputs = new ArrayList<Double>();
		ArrayList<Double> inputs = new ArrayList<Double>();
		String stringToLog = new String();
		
		outputs = new ArrayList<Double>();
		inputs = example.getInputs();
		try 
		{
			outputs = process(inputs);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		for(int j=0;j<outputs.size();j++)
		{
			stringToLog += outputs.get(j);
			stringToLog += ",";
		}
		return stringToLog += "\n";
	}
	
	public void sendOutputToLog()
	{
		String stringToLog = new String();
		
		// Do przemyslenia: examples i test examples czy tylko testexamples
		for(int i=0;i<examples.size();i++)
		{
			stringToLog += makeStringToLog(examples.get(i));
		}
		for(int i=0;i<testExamples.size();i++)
		{
			stringToLog += makeStringToLog(testExamples.get(i));
		}
		if(logPath!=null) 
		{
			clearLog();
			log(stringToLog);
		}
	}
	
	private void getExamplesFromFile(String path, int inputs, int outputs, ArrayList<TrainingExample> target) throws Exception
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
				target.add(example);
			}
			scanner.close();
		}
		catch(Exception e)
		{
			throw new Exception();
		}
	}
	
	public void getTrainingExamplesFromFile(String path, int inputs, int outputs) throws Exception
	{
		getExamplesFromFile(path, inputs, outputs, this.examples);
	}
	
	public void getTestExamplesFromFile(String path, int inputs, int outputs) throws Exception
	{
		getExamplesFromFile(path, inputs, outputs, this.testExamples);
	}
	
	private void clearLog()
	{
		FileWriter writer;
		try 
		{
			File file = logPath.getParent().toFile();
			file.mkdirs();
			writer = new FileWriter(logPath.toFile());
			writer.write("");
			writer.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void log(String text)
	{
		File file = logPath.getParent().toFile();
		file.mkdirs();
		FileWriter writer;
		try 
		{
			writer =  new FileWriter(logPath.toFile(),true);
			writer.write(text+"\n");
			writer.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	//Tylko do zadania na 5
	private void formatOneOutput(ArrayList<Double> outputs)
	{
		double n = outputs.get(0);
		outputs.clear();
		for(int j=1; j<=3; j++)
		{
			if(Math.abs(n-(double)j)<0.1)outputs.add(1.0);
			else outputs.add(0.0);
		}
	}
	
	public void formatExampleOutputs()
	{
		for(int i=0; i<examples.size(); i++)
		{
			formatOneOutput(examples.get(i).getOutputs());
		}
		for(int i=0; i<testExamples.size(); i++)
		{
			formatOneOutput(testExamples.get(i).getOutputs());
		}
	}

}
