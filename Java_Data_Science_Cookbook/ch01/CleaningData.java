package com.kilhyunkim.DS;

public class CleaningData {

	public static void main(String[] args) 
	{
		CleaningData clean = new CleaningData();
		String text = "Your text here you have got from some file";
		String cleanedText = clean.cleanText(text);
		
		System.out.println(cleanedText);
	}
	
	public String cleanText(String text)
	{
		text = text.replaceAll("[^\\p{ASCII}]", "");
		text = text.replaceAll("\\s+", " ");
		text = text.replaceAll("\\p{Cntrl}", "");
		text = text.replaceAll("^\\p{Print}]", "");
		text = text.replaceAll("\\p{C}", "");
		
		return text;
	}
}
