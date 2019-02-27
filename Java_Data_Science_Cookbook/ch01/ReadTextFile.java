package com.kilhyunkim.DS;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ReadTextFile 
{
	public static void main(String[] args)
	{
		ReadTextFile test = new ReadTextFile();
		test.readTextFile("C:/workspace/Java/JavaDS/Data/dummy.txt");
	}
	
	public void readTextFile(String file)
	{// 읽고자 하는 파일의 경로를 문자열형식의 매개변수를 넘겨 받는다.
		
		// Paths.get()
		// - 매개변수로 파일명과 경로를 지정해준 String 객체를 이용해 
		// - 파일로부터 모든 줄을 하나의 스트립으로 읽어들이고 결과적으로 메소드 출력이 Stream 변수로 향하게 된다. 
		try (Stream<String> stream = Files.lines(Paths.get(file))) 
		{
			stream.forEach(System.out::println);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

	}
}
