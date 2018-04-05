package perceptron;

import java.util.concurrent.ThreadLocalRandom;
import java.lang.Math;

public class LogisticNeuron extends Neuron
{
	//https://stats.stackexchange.com/questions/47590/what-are-good-initial-weights-in-a-neural-network
	protected void initializeWeights(int n)
	{
		double range = 1/( Math.sqrt(n) );
		for(int i=0; i<n; i++)
		{
			weights.add(ThreadLocalRandom.current().nextDouble(-range, range));
		}
	}
	
	protected double activationFunction(double sum)
	{
		double result = Math.exp(-sum)+1;
		result = 1.0/result;
		return result;
	}

	protected double derivativeOfActivationFunction(double sum) 
	{
		return activationFunction(sum)*(1-activationFunction(sum));
	}
}
