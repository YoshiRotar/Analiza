package perceptron;

import java.util.ArrayList;

public class Perceptron 
{
	private ArrayList<Double> initialInputs = new ArrayList<Double>();
	private ArrayList<Layer> layers = new ArrayList<Layer>();
	
	public void initializePerceptron() throws Exception
	{
		//+1 dlatego, ze bias
		if(layers.size()<1) throw new Exception();
		layers.get(0).initializeNeurons(initialInputs.size()+1);
		for(int i=1; i<layers.size(); i++)
		{
			layers.get(i).initializeNeurons(layers.get(i-1).getNumberOfNeurons()+1);
		}
	}
	
	public ArrayList<Double> process() throws Exception
	{
		if(layers.size()<1) throw new Exception();
		ArrayList<Double> inputs = initialInputs;
		for(int i=0; i<layers.size(); i++)
		{
			//bias
			inputs.add(1.0);
			layers.get(i).setInputs(inputs);
			layers.get(i).process();
			inputs = layers.get(i).getOutputs(); 
		}
		return inputs;
	}

}
