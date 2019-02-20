package com.kilhyunkim.ML;

import java.io.File;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.Logistic;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.supervised.instance.Resample;
import weka.filters.supervised.instance.StratifiedRemoveFolds;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.filters.unsupervised.attribute.Remove;


public class FraudDetect {

	public static void main(String[] args) throws Exception 
	{
		String filePath = "Data/claims.csv";
		
		CSVLoader loader = new CSVLoader();
		loader.setFieldSeparator(",");
		loader.setSource(new File(filePath));
		Instances data = loader.getDataSet();

		NumericToNominal toNominal = new NumericToNominal();
		toNominal.setInputFormat(data);
		data = Filter.useFilter(data, toNominal);
		
		int CLASS_INDEX = 15;
		data.setClassIndex(CLASS_INDEX);
		
		int POLICY_INDEX = 17;
		Remove remove = new Remove();
		remove.setInputFormat(data);
		remove.setOptions(new String[] {"-R", ""+POLICY_INDEX});
		data = Filter.useFilter(data, remove);
		
		System.out.println(data.toSummaryString());
		System.out.println("Class attribute:\n"+data.attributeStats(data.classIndex()));		
				
		ArrayList<Classifier> models = new ArrayList<Classifier>();
		models.add(new J48());
		models.add(new RandomForest());
		models.add(new NaiveBayes());
		models.add(new AdaBoostM1());
		models.add(new Logistic());
		
		int FOLDS = 3;
		Evaluation eval = new Evaluation(data);
		
		int NO_FRAUD = 0, FRAUD = 1;
		for(Classifier model : models)
		{
			eval.crossValidateModel(model, data, FOLDS, new Random(1), new String[] {});
			System.out.println(model.getClass().getName() + "\n" +
			"\tRecall 		: 	" + eval.recall(FRAUD) + "\n" +
			"\tPrecision	:	" + eval.precision(FRAUD) + "\n" +
			"\tF-measure	: 	" + eval.fMeasure(FRAUD));
		}
		
		System.out.println("\n<=========Modified=========>\n");
		
		StratifiedRemoveFolds kFold = new StratifiedRemoveFolds();
		kFold.setInputFormat(data);
		
		double measures[][] = new double[models.size()][3];
		
		for(int k = 1; k <= FOLDS; k++)
		{
			kFold.setOptions(new String[] {"-N", ""+FOLDS, "-F", ""+k, "-S", "1"});
			Instances test = Filter.useFilter(data, kFold);
			
			kFold.setOptions(new String[] {"-N", ""+FOLDS, "-F", ""+k, "-S", "1", "-V"});
			Instances train = Filter.useFilter(data, kFold);
			
			Resample resample = new Resample();
			resample.setInputFormat(data);
			resample.setOptions(new String[] {"-Z", "100", "-B", "1"});
			
			Instances balancedTrain = Filter.useFilter(train, resample);
			
			for(ListIterator<Classifier> it = models.listIterator(); it.hasNext();)
			{
				Classifier model = it.next();
				model.buildClassifier(balancedTrain);
				eval = new Evaluation(balancedTrain);
				eval.evaluateModel(model, test);
				
				measures[it.previousIndex()][0] += eval.recall(FRAUD);
				measures[it.previousIndex()][1] += eval.precision(FRAUD);
				measures[it.previousIndex()][2] += eval.fMeasure(FRAUD);
			}
		}
		for(int i = 0; i< models.size(); i++)
		{
			measures[i][0] /= 1.0 * FOLDS;
			measures[i][1] /= 1.0 * FOLDS;
			measures[i][2] /= 1.0 * FOLDS;
		}
			
		Classifier bestModel = null;
		double bestScore = -1;
		
		for(ListIterator<Classifier> it = models.listIterator(); it.hasNext();)
		{
			Classifier model = it.next();
			double fMeasure = measures[it.previousIndex()][2];
			
			System.out.println(model.getClass().getName() + "\n" +
			"\tRecall :			" + measures[it.previousIndex()][0] + "\n" +
			"\tPrecision :		" + measures[it.previousIndex()][1] + "\n" + 
			"\tF-measure :		" + fMeasure);
			if(fMeasure > bestScore)
			{
				bestScore = fMeasure;
				bestModel = model;
			}
		}
		System.out.println("Best model:" + bestModel.getClass().getName());	
	}
}
