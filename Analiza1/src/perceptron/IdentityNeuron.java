package perceptron;

public class IdentityNeuron extends Neuron
{
	protected void initializeWeights(int n)
	{
		for(int i=0; i<n; i++)
		{
			weights.add(1.0);
		}
	}
	
	protected double activationFunction(double sum)
	{
		return sum;
	}
	
	
}
