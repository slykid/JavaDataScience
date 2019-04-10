package com.kilhyun.DS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

public class WekaTrainTest {

	NaiveBayes nb;
	Instances train, test, labeled;
	
	public static void main(String[] args) 
	{
		WekaTrainTest test = new WekaTrainTest();
		test.loadModel("nb.model");
		test.loadDatasets("Data/weka_data/iris.arff", "Data/weka_data/iris-test.arff");
		test.classify();
		test.writeArff("Data/iris-result.arff");
	}
	
	public void loadModel(String modelPath)
	{
		try
		{
			nb = new NaiveBayes();
			weka.core.SerializationHelper.read(modelPath);
		}
		catch(Exception e) {
			
		}
	}
	
	public void loadDatasets(String training, String testing)
	{
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(training));
			train = new Instances(reader);
			train.setClassIndex(train.numAttributes() -1);
		}
		catch (IOException e) {
			
		}
		
		try
		{
			reader = new BufferedReader(new FileReader(testing));
			test = new Instances(reader);
			test.setClassIndex(train.numAttributes() -1);
		}
		catch(IOException e) {
			
		}
		
		try
		{
			reader.close();
		}
		catch (IOException e) {
			
		}
	}
	
	public void classify()
	{
		try
		{
			nb.buildClassifier(train);
		}
		catch(Exception e) {
			
		}
		
		labeled = new Instances(test);
		
		for(int i = 0; i < test.numInstances(); i++)
		{
			double clsLabel;
			
			try
			{
				clsLabel = nb.classifyInstance(test.instance(i));
				labeled.instance(i).setClassValue(clsLabel);
				double[] predictionOutput = nb.distributionForInstance(test.instance(i));
				double predictionProbability = predictionOutput[1];
				
				System.out.println(predictionProbability);
			}
			catch (Exception e) {
				
			}
		}
	}
	
	public void writeArff(String outArff)
	{
		BufferedWriter writer;
		
		try
		{
			writer = new BufferedWriter(new FileWriter(outArff));
			writer.write(labeled.toString());
			writer.close();
		}
		catch (Exception e) {
			
		}
	}
}
