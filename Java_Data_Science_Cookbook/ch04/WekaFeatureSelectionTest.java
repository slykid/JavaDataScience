package com.kilhyun.DS;

import java.util.Random;

import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;

public class WekaFeatureSelectionTest {

	Instances iris = null;
	NaiveBayes nb;
		
	public static void main(String[] args) 
	{
		WekaFeatureSelectionTest test = new WekaFeatureSelectionTest();
		test.loadArff("Data/weka_data/iris.arff");
		test.selectFeatures();
		test.selectFeaturesWithFilter();
		test.selectFeaturesWithClassifiers();
	}
	
	public void loadArff(String arffInput)
	{
		DataSource source = null;
		
		try
		{
			source = new DataSource(arffInput);
			iris = source.getDataSet();
			iris.setClassIndex(iris.numAttributes() - 1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void selectFeatures()
	{
		AttributeSelection attrSelection = new AttributeSelection();
		CfsSubsetEval eval = new CfsSubsetEval();
		BestFirst search = new BestFirst();
		attrSelection.setEvaluator(eval);
		attrSelection.setSearch(search);
		
		try
		{
			attrSelection.SelectAttributes(iris);
			int[] attrIndex = attrSelection.selectedAttributes();
			
			System.out.println(Utils.arrayToString(attrIndex));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void selectFeaturesWithFilter()
	{
		weka.filters.supervised.attribute.AttributeSelection filter = new weka.filters.supervised.attribute.AttributeSelection();
		CfsSubsetEval eval = new CfsSubsetEval();
		BestFirst search = new BestFirst();
		filter.setEvaluator(eval);
		filter.setSearch(search);
		try
		{
			filter.setInputFormat(iris);
			Instances newData = Filter.useFilter(iris, filter);
			System.out.println(newData);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void selectFeaturesWithClassifiers()
	{
		AttributeSelectedClassifier classifier = new AttributeSelectedClassifier();
		CfsSubsetEval eval = new CfsSubsetEval();
		BestFirst search = new BestFirst();
		
		nb = new NaiveBayes();
		
		classifier.setClassifier(nb);
		classifier.setEvaluator(eval);
		classifier.setSearch(search);
		Evaluation evaluation;
		
		try
		{
			evaluation = new Evaluation(iris);
			evaluation.crossValidateModel(classifier, iris, 10, new Random(1));
			System.out.println(evaluation.toSummaryString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
