package com.kilhyunkim.DS;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class WriteJSON {

	public static void main(String[] args) 
	{
		JSONObject obj = new JSONObject();
		
		obj.put("book", "Harry Potter");
		obj.put("author", "J.K. Rowling");
		
		JSONArray list = new JSONArray();
		list.add("There are characters in this vook that will remind us of all the people we have met.Everybody knows or knew a spoilt, overweight boy like Dudley pr a bossy and inter fering(yet kind-hearted) girl like Hermione");
		list.add("Hogwarts is a truly magical place, not only in the most obvious way but also in all the detail that the author has gone to describe it so vibrantly.");
		list.add("Parents need to know that this thrill-a-,inute story, the first in th Harry Potter series, respects kids' intelligence and motivates them to tackle its greater length and complexity, play imaginative games, and try to solve its logic puzzles.");

		obj.put("messages", list);
		
		try
		{
			FileWriter file = new FileWriter("C:/workspace/Java/JavaDS/Data/test.json");
			file.write(obj.toJSONString());
			file.flush();
			file.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		System.out.print(obj);
	}

}
