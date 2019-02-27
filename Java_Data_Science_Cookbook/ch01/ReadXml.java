package com.kilhyunkim.DS;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


public class ReadXml {

	public static void main(String[] args) 
	{
		SAXBuilder builder = new SAXBuilder();
		
		File file = new File("C:/workspace/Java/JavaDS/Data/dummyxml.xml");
		
		try
		{
			Document doc = (Document)builder.build(file);
			Element rootNode = doc.getRootElement();
			List list= rootNode.getChildren("author");
			
			for(int i = 0; i < list.size(); i++)
			{
				Element node = (Element) list.get(i);
				System.out.println("First Name : " + node.getChildText("firstname"));
				System.out.println("Last Name:" + node.getChildText("lastname"));
			}
		}
		
		catch(IOException io)
		{
			System.out.println(io.getMessage());
		}
		
		catch(JDOMException jdomex)
		{
			System.out.println(jdomex.getMessage());
		}
	}

}
