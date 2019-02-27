package com.kilhyunkim.DS;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadJSON {

	public static void main(String[] args) 
	{
		JSONParser parser = new JSONParser();
		
		try
		{
			Object obj = parser.parse(new FileReader("C:/workspace/Java/JavaDS/Data/test.json"));
			JSONObject jsonObject = (JSONObject) obj;
			
			String name = (String) jsonObject.get("book");
			System.out.println(name);
			
			String author = (String) jsonObject.get("author");
			System.out.println(author);
			
			JSONArray reviews = (JSONArray) jsonObject.get("messages");
			Iterator<String> iterator = reviews.iterator();
			while(iterator.hasNext())
				System.out.println(iterator.next());
		}
		
		catch(FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}
	}

}
