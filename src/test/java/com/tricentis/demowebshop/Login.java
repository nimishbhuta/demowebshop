package com.tricentis.demowebshop;



import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.tricentis.utilities.commonUtils;
import com.tricentis.utilities.excelUtils;
import com.tricentis.utilities.webUtils;




public class Login {

WebDriver driver;
String currMonthValue;
String currYearValue;
String cardCode;
Double priceValue;
WebDriverWait wait;
@BeforeClass

public void Initialize()  {
	
	 //options.addArguments("disable-infobars");

	//Load the excel file into the array
	
	excelUtils readExcel=new excelUtils("C:\\Projects\\Selenium\\demowebshop\\src\\test\\java\\com\\tricentis\\testdata\\Shopping_TestData.xlsx");
	String userName=readExcel.getCellData("Sheet1", "Username", 2);
	
	 System.setProperty("webdriver.chrome.driver","C:\\Projects\\Selenium\\demowebshop\\src\\test\\java\\driver\\chromedriver.exe");
	 ChromeOptions options = new ChromeOptions();
	  options.setPageLoadStrategy(PageLoadStrategy.NONE);
	  driver=new ChromeDriver(options);
	  webUtils webOperation=new webUtils();
	  webOperation.launchURL(driver, "http://demowebshop.tricentis.com/");
}


@DataProvider
public Object[][] testData(){
	Object[][] data= new Object[1][2];
	data[0][0]="nbhuta@siisweden.test";
	data[0][1]="Quality@123";
 	return data;
}


  @Test(priority=1,dataProvider="testData")
  public void Login_Test(String loginWebShop,String passwordWebShop ) {
	  	  
	  driver.findElement(By.className("ico-login")).click();
	  driver.findElement(By.id("Email")).sendKeys(loginWebShop);
	  driver.findElement(By.id("Password")).sendKeys(passwordWebShop);
	  //driver.findElement(By.className("button-1 login-button")).click();
	  driver.findElement(By.xpath("//input[contains(@class,'login-button')]")).click();	
	  WebElement logOut=driver.findElement(By.className("ico-logout"));
	  if (logOut.isDisplayed()==true){
		  System.out.println("The user has logged into the web shop");  
	  }
	  else{
		  System.out.println("The user has not logged into the web shop");
	  }

	  driver.findElement(By.linkText("Apparel & Shoes")).click();//Click on the Apparel Shoes
	  driver.findElement(By.linkText("Blue Jeans")).click(); //Click on blue jeans 
	  priceValue=Double.valueOf(driver.findElement(By.xpath("//span[contains(@itemprop,'price')]")).getText());
	  driver.findElement(By.className("qty-input")).clear();
	  driver.findElement(By.className("qty-input")).sendKeys("25");
	  driver.findElement(By.id("add-to-cart-button-36")).click();//Click on Add to Cart	
	  wait=new WebDriverWait(driver,100);
	  wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id='topcartlink']/a/span[1]"))));
	  driver.findElement(By.xpath("//*[@id='topcartlink']/a/span[1]")).click();
	  driver.findElement(By.id("termsofservice")).click();
  }
  
  @Test(priority=2)

  public void OrderProduct_Test() {
	  
	  	  
	  //Click 
	  driver.findElement(By.id("checkout")).click();//Click on Add to Cart	
	  driver.findElement(By.xpath("//input[@title='Continue']")).click();//Billing Address
	  driver.findElement(By.xpath("//*[@id='shipping-buttons-container']/input")).click();//Shipping Address
	  driver.findElement(By.id("shippingoption_0")).click();
	  driver.findElement(By.xpath("//*[@id='shipping-method-buttons-container']/input")).click();//Shipping Method
	  driver.findElement(By.id("paymentmethod_2")).click();
	  driver.findElement(By.xpath("//*[@id='payment-method-buttons-container']/input")).click();//Payment Method
	  //Payment Information
	  commonUtils commonOperation=new commonUtils();
	  System.out.println(commonOperation.getCurrentMonth());
	  currMonthValue=String.valueOf(commonOperation.getCurrentMonth() + 1);		  
	  currYearValue=String.valueOf(commonOperation.getCurrentYear() + 1);
	  cardCode=String.valueOf((int) (Math.random() + 1) * 300);
	  wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("CreditCardType"))));
	  //driver.findElement(By.xpath("//*[@id='topcartlink']/a/span[1]")).click();
	  Select oSelectCreditCard= new Select(driver.findElement(By.id("CreditCardType")));
	  oSelectCreditCard.selectByValue("Visa");
	  driver.findElement(By.id("CardholderName")).sendKeys("NimishBhuta");
	  driver.findElement(By.id("CardNumber")).sendKeys("4485564059489345");
	  Select oSelectExpireMonth= new Select(driver.findElement(By.id("ExpireMonth")));
	  oSelectExpireMonth.selectByValue(currMonthValue); // selectByValue is used as the value is coming as single digit like 3 rather than 03
	  Select oSelectExpireYear= new Select(driver.findElement(By.id("ExpireYear")));	
	  oSelectExpireYear.selectByVisibleText(currYearValue);
	  driver.findElement(By.id("CardCode")).sendKeys(cardCode);
	  //driver.findElement(By.xpath("//input[contains(@class,'new-address-next-step-button')]")).click();//Payment Information
	  driver.findElement(By.xpath("//*[@id='payment-info-buttons-container']/input")).click();//Payment Information
	  
	//Verification of Prices 
	  
	  // Get the Subtotal from the table 
	  
	   Double subTotal=Double.valueOf(driver.findElement(By.xpath("//span[contains(@class,'product-price')]")).getText());
       System.out.println(subTotal);
       Double expSubTotal=priceValue * 25;
       //String.valueOf(expSubTotal)==String.valueOf(subTotal)
       SoftAssert assertion=new SoftAssert();
       assertion.assertEquals(subTotal,expSubTotal);
       
       if(String.valueOf(expSubTotal).equals(String.valueOf(subTotal))){
    	   System.out.println("The expected value " +String.valueOf(expSubTotal)+"matches with the actual value"+String.valueOf(subTotal));
       }
       else{
    	   System.out.println("The expected value " +String.valueOf(expSubTotal)+"does not match with the actual value"+String.valueOf(subTotal));   
       }
    	   
	  driver.findElement(By.xpath("//*[@id='confirm-order-buttons-container']/input")).click();//Confirm Order 
	  //driver.findElement(By.xpath("//input[@value='Continue']")).click();
	  System.out.println("This is the title at Order Product Test"+ driver.getTitle());
	  
  }

  @Test(priority=3)

  public void Payment_Test() {
	  System.out.println(driver.getTitle());
	  System.out.println("This is the title "+ driver.getTitle());  
	  driver.findElement(By.xpath("//input[contains(@class,'continue-button')]")).click();
	  
	  //driver.findElement(By.xpath("//html/body/div[4]/div[1]/div[4]/div/div/div[2]/div/div[2]/input")).click();
	  
  }

  
}
