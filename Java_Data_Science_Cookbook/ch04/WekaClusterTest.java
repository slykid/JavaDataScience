package com.kilhyun.DS;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class WekaClusterTest {

	Instances cpu = null;
	SimpleKMeans kmeans;
		
	public static void main(String[] args) 
	{
		WekaClusterTest test = new WekaClusterTest();
		test.loadArff("Data/weka_data/cpu.arff");
		test.clusterData();
	}
	
	public void loadArff(String arffInput)
	{
		DataSource source = null;
		try
		{
			source = new DataSource(arffInput);
			cpu = source.getDataSet();
			cpu.setClassIndex(cpu.numAttributes() -1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void clusterData()
	{
		kmeans = new SimpleKMeans();
		kmeans.setSeed(10);
		
		try
		{
			kmeans.setPreserveInstancesOrder(true);
			kmeans.setNumClusters(10);
			kmeans.buildClusterer(cpu);
			int[] assignments = kmeans.getAssignments();
			int i = 0;
			for(int clusterNum : assignments)
			{
				System.out.printf("Instance %d -> Cluster %d\n", i, clusterNum);
				i++;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
