package com.kilhyun.DS;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class WekaArffTest {

	public static void main(String[] args) throws Exception 
	{
		ArrayList<Attribute> attributes;
		ArrayList<String> classVals;
		Instances data;
		double[] values;
		
		//속성을 담을 ArrayList선언
		attributes = new ArrayList<Attribute>();
		attributes.add(new Attribute("age"));
		
		ArrayList<String> empty = null;
		attributes.add(new Attribute("name", empty));
		
		// 날짜 속성
		attributes.add(new Attribute("dob", "yyyy-MM-dd"));
		classVals = new ArrayList<String>();
		
		for(int i = 0; i < 5; i++)
			classVals.add("class" + (i+1));
		
		Attribute classVal = new Attribute("class", classVals);
		attributes.add(classVal);
				
		// Instances 객체 생성
		data = new Instances("MyRelation", attributes, 0);
		
		// 데이터 채우기
		/// 첫 인스턴스
		values = new double[data.numAttributes()];
		values[0] = 35;
		values[1] = data.attribute(1).addStringValue("John Doe");
		values[2] = data.attribute(2).parseDate("1981-01-20");
		values[3] = classVals.indexOf("class3");
		data.add(new DenseInstance(1.0, values));
		
		/// 두 번째 인스턴스
		values = new double[data.numAttributes()];
		values[0] = 30;
		values[1] = data.attribute(1).addStringValue("Harry Potter");
		values[2] = data.attribute(2).parseDate("1986-07-05");
		values[3] = classVals.indexOf("class1");
		data.add(new DenseInstance(1.0, values));
		
		/// 세 번째 인스턴스
		values = new double[data.numAttributes()];
		values[0] = 27;
		values[1] = data.attribute(1).addStringValue("Kilhyun Kim");
		values[2] = data.attribute(2).parseDate("1993-10-08");
		values[3] = classVals.indexOf("class2");
		data.add(new DenseInstance(1.0, values));
		
		// 디스크에 arff 파일 저장
		BufferedWriter writer = new BufferedWriter(new FileWriter("Data/train.arff"));
		writer.write(data.toString());
		writer.close();
		
		System.out.println(data);
	}

}
