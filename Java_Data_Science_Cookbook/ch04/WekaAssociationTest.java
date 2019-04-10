package com.kilhyun.DS;

import weka.associations.Apriori;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class WekaAssociationTest {

	Instances superMarket = null;
	Apriori apriori;
	
	public static void main(String[] args) 
	{
		WekaAssociationTest test = new WekaAssociationTest();
		test.loadArff("Data/weka_data/supermarket.arff");
		test.generateRule();
	}
	
	public void loadArff(String arffInput)
	{
		DataSource source = null;
		
		try
		{
			source = new DataSource(arffInput);
			superMarket = source.getDataSet();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void generateRule()
	{
		apriori = new Apriori();
		
		try
		{
			apriori.buildAssociations(superMarket);
			System.out.println(apriori);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
