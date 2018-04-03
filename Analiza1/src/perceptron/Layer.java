package perceptron;

import java.util.ArrayList;

public class Layer 
{
	private ArrayList<Neuron> neurons = new ArrayList<Neuron>();
	private ArrayList<Double> inputs = new ArrayList<Double>();
	private ArrayList<Double> outputs = new ArrayList<Double>();
	
	public int getNumberOfNeurons()
	{
		return neurons.size();
	}
	
	public void setInputs(ArrayList<Double> inputs)
	{
		this.inputs = inputs;
	}
	
	public ArrayList<Double> getOutputs()
	{
		return outputs;
	}
	
	public void process()
	{
		for(int i = 0; i<neurons.size(); i++)
		{
			Neuron neuron = neurons.get(i);
			neuron.setInputs(inputs);
			neuron.process();
			outputs.set(i, neuron.getOutput());
		}
	}
	
	public void initializeNeurons(int n)
	{
		for (int i=0; i<neurons.size(); i++)
		{
			neurons.get(i).initializeWeights(n);
		}
	}
}
