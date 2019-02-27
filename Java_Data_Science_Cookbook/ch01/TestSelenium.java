package com.kilhyunkim.DS;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestSelenium {

	public static void main(String[] args) throws InterruptedException 
	{
		TestSelenium test = new TestSelenium();
		String webData = test.extractDataWithSelenium("http://cogenglab.csd.uwo.ca/rushdi.htm");
		
		System.out.println(webData);
	}
	
	public String extractDataWithSelenium(String url) throws InterruptedException
	{
		// 웹드라이버.exe 파일위치 설정
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();
		
		driver.get(url);
		Thread.sleep(5000);
		
		WebElement webElement = driver.findElement(By.xpath("//*[@id='content']"));
		
		System.out.println(webElement.getText());
		
		return url;
	}

}
