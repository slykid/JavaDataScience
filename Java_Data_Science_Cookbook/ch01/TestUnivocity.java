package com.kilhyunkim.DS;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

public class TestUnivocity {

	public static void main(String[] args) 
	{
		TestUnivocity test = new TestUnivocity();
		test.parseCSV("C:/workspace/Java/JavaDS/Data/testCSV.csv");
	}
	
	public void parseCSV(String file)
	{
		CsvParserSettings parserSettings = new CsvParserSettings();
		parserSettings.setLineSeparatorDetectionEnabled(true);
		RowListProcessor rowProcessor = new RowListProcessor();
		parserSettings.setRowProcessor(rowProcessor);
		parserSettings.setHeaderExtractionEnabled(true);
		CsvParser parser = new CsvParser(parserSettings);
		parser.parse(new File(file));
		
		String[] headers = rowProcessor.getHeaders();
		List<String[]> rows = rowProcessor.getRows();
		for(int i = 0; i <rows.size(); i++)
		{
			System.out.println(Arrays.asList(rows.get(i)));
		}
	}
}
