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
	
	abstract protected double derivativeOfActivationFunction(double sum);
	
	abstract protected void initializeWeights(int n);
	
	protected double errorOfNeuron(double error)
	{
		return error*derivativeOfActivationFunction(sum());
	}
	
	//Funkcja zwraca wektor bledow dla poszczegolnych wag
	protected ArrayList<Double> backPropagation(double error, double rateOfChange)
	{
		ArrayList<Double> errors = new ArrayList<Double>();
		double errorOfNeuron = errorOfNeuron(error);
		for(int i=0; i<weights.size(); i++)
		{
			errors.add(errorOfNeuron*weights.get(i));
		}
		for(int i=0; i<weights.size(); i++)
		{
			weights.set(i, weights.get(i)-rateOfChange*errorOfNeuron*inputs.get(i));
		}
		return errors;
	}
	
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
