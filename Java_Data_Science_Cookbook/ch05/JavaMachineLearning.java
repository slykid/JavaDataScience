package org.kilhyun.datascience;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.CrossValidation;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.clustering.evaluation.ClusterEvaluation;
import net.sf.javaml.clustering.evaluation.SumOfSquaredErrors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.distance.PearsonCorrelationCoefficient;
import net.sf.javaml.featureselection.ranking.RecursiveFeatureEliminationSVM;
import net.sf.javaml.featureselection.scoring.GainRatio;
import net.sf.javaml.featureselection.subset.GreedyForwardSelection;
import net.sf.javaml.tools.data.FileHandler;

public class JavaMachineLearning {

	public static void main(String[] args) throws IOException 
	{
		Dataset data = FileHandler.loadDataset(new File("data/javaml-0.1.7/UCI-small/iris/iris.data"), 4, ",");
		System.out.println(data);
		System.out.println();
		FileHandler.exportDataset(data, new File("data/javaml-output.txt"));
		
		data = FileHandler.loadDataset(new File("data/javaml-output.txt"), 0, "\t");
		System.out.println(data);
		System.out.println();
		
		Clusterer km = new KMeans();
		Dataset[] clusters = km.cluster(data);
		
		for (Dataset cluster:clusters)
			System.out.println("Cluster: " + cluster);
		System.out.println();
		
		ClusterEvaluation sse = new SumOfSquaredErrors();
		double score = sse.score(clusters);
		System.out.println(score);
		System.out.println();
		
		Classifier knn = new KNearestNeighbors(5);
		knn.buildClassifier(data);
		
		CrossValidation cv = new CrossValidation(knn);
		Map<Object, PerformanceMeasure> cvEvaluation = cv.crossValidation(data);
		System.out.println(cvEvaluation);
		System.out.println();
		
		Dataset testData = FileHandler.loadDataset(new File("data/javaml-0.1.7/UCI-small/iris/iris.data"), 4, ",");
		Map<Object, PerformanceMeasure> testEvaluation = EvaluateDataset.testDataset(knn, testData);
		
		for(Object classVariable:testEvaluation.keySet())
			System.out.println(classVariable + " class has " + testEvaluation.get(classVariable).getAccuracy());
		System.out.println();
		
		GainRatio gainRatio = new GainRatio();
		gainRatio.build(data);
		for(int i = 0; i < gainRatio.noAttributes(); i++)
			System.out.println(gainRatio.score(i));
		System.out.println();
		
		RecursiveFeatureEliminationSVM featureRank = new RecursiveFeatureEliminationSVM(0.2); 
		featureRank.build(data);
		for (int i = 0; i < featureRank.noAttributes(); i++)
			System.out.println(featureRank.rank(i));
		System.out.println();
		
		GreedyForwardSelection featureSelection = new GreedyForwardSelection(5, new PearsonCorrelationCoefficient()); 
		featureSelection.build(data);
		System.out.println(featureSelection.selectedAttributes());
		System.out.println();
	}

}
