package com.kilhyun.DS;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.EM;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class WekaClassesToClusterTest {

	Instances weather;
	EM clusterer;
	
	public static void main(String[] args) 
	{
		WekaClassesToClusterTest test = new WekaClassesToClusterTest();
		test.loadArff("Data/weka_data/weather.nominal.arff");
		test.generateClassToCluster();
	}

	public void loadArff(String arffInput)
	{
		DataSource source = null;
		
		try
		{
			source = new DataSource(arffInput);
			weather = source.getDataSet();
			weather.setClassIndex(weather.numAttributes() -1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void generateClassToCluster()
	{
		Remove filter = new Remove();
		filter.setAttributeIndices("" + (weather.classIndex() + 1));
		
		try
		{
			filter.setInputFormat(weather);
			Instances dataClusterer = Filter.useFilter(weather, filter);
			
			clusterer = new EM();
			clusterer.buildClusterer(dataClusterer);
			
			ClusterEvaluation eval = new ClusterEvaluation();
			eval.setClusterer(clusterer);
			eval.evaluateClusterer(weather);
			
			System.out.println(eval.clusterResultsToString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
