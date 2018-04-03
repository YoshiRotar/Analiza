package perceptron;

import java.util.Random;

public class LogisticNeuron extends Neuron
{
	
	// ???
	protected void initializeWeights(int n)
	{
		Random random = new Random();
		for(int i=0; i<n; i++)
		{
			weights.add(random.nextDouble());
		}
	}
	
	protected double activationFunction(double sum)
	{
		double result = Math.exp(-sum)+1;
		result = 1.0/result;
		return result;
	}
}
