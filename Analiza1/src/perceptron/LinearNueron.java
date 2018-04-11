package perceptron;

import java.util.concurrent.ThreadLocalRandom;

public class LinearNueron extends Neuron
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
		return sum;
	}

	protected double derivativeOfActivationFunction(double sum) 
	{
		return 1;
	}
}
