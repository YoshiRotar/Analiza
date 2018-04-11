package perceptron;

import java.util.ArrayList;

public class Layer 
{
	private ArrayList<Neuron> neurons = new ArrayList<Neuron>();
	private ArrayList<Double> inputs = new ArrayList<Double>();
	private ArrayList<Double> outputs = new ArrayList<Double>();
	
	public void addNeuron(Neuron n)
	{
		neurons.add(n);
	}
	
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
	
	public void fillWithLogisticNeurons(int n)
	{
		Neuron neuron;
		for(int i=0; i<n; i++)
		{
			neuron = new LogisticNeuron();
			addNeuron(neuron);
		}
	}
	
	public void process()
	{
		outputs = new ArrayList<Double>();
		for(int i = 0; i<neurons.size(); i++)
		{
			Neuron neuron = neurons.get(i);
			neuron.setInputs(inputs);
			neuron.process();
			outputs.add(i, neuron.getOutput());
		}
	}
	
	public void initializeNeurons(int n)
	{
		for (int i=0; i<neurons.size(); i++)
		{
			neurons.get(i).initializeWeights(n);
		}
	}
	
	//zwraca wektor bledow dla nastepnej warstwy neuronow
	public ArrayList<Double> backPropagation(ArrayList<Double> errors, double rateOfChange) throws Exception
	{
		if(neurons.size()<1) throw new Exception();
		ArrayList<Double> errorsForNext = neurons.get(0).backPropagation(errors.get(0), rateOfChange);
		for(int i=1; i<neurons.size(); i++)
		{
			ArrayList<Double> errorsFromCurrentNeuron = neurons.get(i).backPropagation(errors.get(i), rateOfChange);
			if(errorsFromCurrentNeuron.size()!=errorsForNext.size()) throw new Exception();
			for(int j=0; j<errorsForNext.size(); j++)
			{
				errorsForNext.set(j, errorsForNext.get(j) + errorsFromCurrentNeuron.get(j));
			}
		}
		return errorsForNext;
	}
}
