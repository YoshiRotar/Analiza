package perceptron;

import java.util.ArrayList;

public abstract class Neuron 
{
	protected ArrayList<Double> inputs = new ArrayList<Double>();
	protected ArrayList<Double> weights = new ArrayList<Double>();
	protected double output;
	protected  ArrayList<Double> lastIncrement = new ArrayList<Double>();
	
	public double getOutput()
	{
		return output;
	}
	
	public void setInputs(ArrayList<Double> inputs)
	{
		this.inputs = inputs;
	}
	
	abstract protected double activationFunction(double sum);
	
	abstract protected double derivativeOfActivationFunction(double sum);
	
	abstract protected void initializeWeights(int n);
	
	protected double errorOfNeuron(double error)
	{
		return error*derivativeOfActivationFunction(sum());
	}
	
	//Funkcja zwraca wektor bledow dla poszczegolnych wag
	protected ArrayList<Double> backPropagation(double error, double rateOfChange, double momentum)
	{
		ArrayList<Double> errors = new ArrayList<Double>();
		double errorOfNeuron = errorOfNeuron(error);
		for(int i=0; i<weights.size(); i++)
		{
			errors.add(errorOfNeuron*weights.get(i));
		}
		for(int i=0; i<weights.size(); i++)
		{
			if(lastIncrement.size()<=i) lastIncrement.add(0.0);
			double newIncrement = (-rateOfChange*errorOfNeuron*inputs.get(i) + momentum*lastIncrement.get(i));
			weights.set(i, weights.get(i)+newIncrement);
			lastIncrement.set(i, newIncrement);
		}
		return errors;
	}
	
	protected double sum()
	{
		double result = 0;
		for(int i=0; i<weights.size(); i++)
		{
			//System.out.println(inputs.size() + " " + weights.size());
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
