package com.kilhyunkim.ML;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.LOF;

public class AnomalyDetect {

	public static void main(String[] args) throws Exception 
	{
		String filePath = "data/ydata/A1Benchmark/real_"; //full name example real_1.cvs
		
		List<List<Double>> rawData = new ArrayList<List<Double>>();
		
		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		
		for(int i = 1; i <= 67; i++)
		{
			List<Double> sample = new ArrayList<Double>();
			BufferedReader reader = new BufferedReader(new FileReader(filePath+i+".csv"));
			
			boolean isAnomaly = false;
			reader.readLine();
			while(reader.ready())
			{
				String line[] = reader.readLine().split(",");
				double value = Double.parseDouble(line[1]);
				sample.add(value);
				
				max = Math.max(max, value);
				min = Double.min(min, value);
				
				if(line[2] == "1")
					isAnomaly = true;
				
			}
			System.out.println(isAnomaly);
			reader.close();
			
			rawData.add(sample);
		}
		
		System.out.println(rawData.size() +"\n\nmax: "+ max + "\nmin: "+ min);

		int WIN_SIZE = 500;
		int HIST_BINS = 20;
		int current = 0;
		
		List<double[]> dataHist = new ArrayList<double[]>();
		for(List<Double> sample : rawData)
		{
			double[] histogram = new double[HIST_BINS];
			for(double value : sample)
			{
				int bin = toBin(normalize(value, min, max), HIST_BINS);
				histogram[bin]++;
				current++;
				if(current == 500)
				{
					current = 0;
					dataHist.add(histogram);
					histogram = new double[HIST_BINS];
				}
			}
			dataHist.add(histogram);
		}
		
		for(double[] histogram : dataHist)
		{
			int sum = 0;
			for(double v : histogram)
				sum += v;
			
			for(int i = 0; i < histogram.length; i++)
				histogram[i] /= 1.0 * sum;
			
		}
		System.out.println("Total histogranms:"+ dataHist.size());

		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		for(int i = 0; i < HIST_BINS; i++)
			attributes.add(new Attribute("Hist-"+i));
		
		Instances dataset = new Instances("My dataset", attributes, dataHist.size());
		for(double[] histogram: dataHist)
			dataset.add(new DenseInstance(1.0, histogram));
		
		System.out.println("Dataset created: "+dataset.size());
		
		Instances trainData = dataset.testCV(2, 0);
		Instances testData = dataset.testCV(2, 1);
		System.out.println("Train: "+trainData.size()+"\nTest:"+testData.size());
		
		LOF lof = new LOF();
		lof.setInputFormat(trainData);
		lof.setOptions(new String[]{"-min", "3", "-max", "3"});
		
		for(Instance inst : trainData)
			lof.input(inst);
		
		lof.batchFinished();
		System.out.println("LOF loaded\n");
		
		Instances testDataLofScore = Filter.useFilter(testData, lof);
		
		for(Instance inst : testDataLofScore)
			System.out.println(inst.value(inst.numAttributes()-1));
	}
	
	// 평균 정규화
	static double normalize(double value, double min, double max)
	{
		return value / (max-min);
	}

	// 이진화 함수
	static int toBin(double normalizedValue, int bins)
	{
		if(normalizedValue == 1.0) return bins-1;
		return (int)(normalizedValue * bins);
	}
}
