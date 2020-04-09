package com.tricentis.utilities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

public class webUtils {
	
	public void launchURL(WebDriver driver,String webURL) {
		
		 driver.manage().window().maximize();
		 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 //driver.get("http://demowebshop.tricentis.com/");
		  driver.get(webURL);
		}

}
