package com.kilhyun.DS;

import weka.classifiers.functions.Logistic;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class WekaLogisticRegressionTest {

	Instances iris = null;
	Logistic logReg;
		
	public static void main(String[] args) 
	{
		WekaLogisticRegressionTest test = new WekaLogisticRegressionTest();
		test.loadArff("Data/weka_data/iris.arff");
		test.buildRegression();
	}
	
	public void loadArff(String arffInput)
	{
		DataSource source = null;
		
		try
		{
			source = new DataSource(arffInput);
			iris = source.getDataSet();
			iris.setClassIndex(iris.numAttributes() -1);
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void buildRegression()
	{
		logReg = new Logistic();
		
		try
		{
			logReg.buildClassifier(iris);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println(logReg);
	}
}
