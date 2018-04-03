package perceptron;

import java.util.ArrayList;

public abstract class Neuron 
{
	protected ArrayList<Double> inputs = new ArrayList<Double>();
	protected ArrayList<Double> weights = new ArrayList<Double>();
	protected double output;
	
	public double getOutput()
	{
		return output;
	}
	
	public void setInputs(ArrayList<Double> inputs)
	{
		this.inputs = inputs;
	}
	
	abstract protected double activationFunction(double sum);
	
	abstract protected void initializeWeights(int n);
	
	protected double sum()
	{
		double result = 0;
		for(int i=0; i<inputs.size(); i++)
		{
			result += inputs.get(i)*weights.get(i);
		}
		return result;
	}
	
	public void process()
	{
		double result = 0;
		result = sum();
		output = activationFunction(result);
	}
}
