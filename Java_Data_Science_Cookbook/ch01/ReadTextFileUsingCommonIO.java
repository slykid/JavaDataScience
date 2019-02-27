package com.kilhyunkim.DS;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class ReadTextFileUsingCommonIO {

	public static void main(String[] args) 
	{
		ReadTextFileUsingCommonIO test = new ReadTextFileUsingCommonIO();
		test.readFile("C:/workspace/Java/JavaDS/Data/dummy.txt");
	}

	public void readFile(String fileName) 
	{
		File file = new File(fileName);
		String text = "";
		
		try
		{
			text = FileUtils.readFileToString(file, "UTF-8");
		}
		catch(IOException e)
		{
			System.out.println("Error reading " + file.getAbsolutePath());
			e.printStackTrace();
		}
	}
}
