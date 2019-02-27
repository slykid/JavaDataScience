package com.kilhyunkim.DS;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class TestRecursiveDirectoryTraversal 
{

	public static void main(String[] args) 
	{
		System.out.println(listFiles(new File("C:/workspace")));
	}
	
	// 파일 검색 메소드
	public static Set<File> listFiles(File rootDir)
	{// 루트 혹은 시작 디렉터리를 매개변수로 지정함
		
		Set<File> fileSet = new HashSet<File>();
		
		// 루트 또는 내부 디렉터리들이 null 인지 확인 
		if(rootDir == null || rootDir.listFiles()==null)
			return fileSet;
		
		// 한 번에 하나씩 파일의 종류를 확인한다.
		for (File fileOrDir : rootDir.listFiles())
		{
			if (fileOrDir.isFile())
				fileSet.add(fileOrDir);
			
			else
				fileSet.addAll(listFiles(fileOrDir));
		}
		return fileSet;
	}
	

}
