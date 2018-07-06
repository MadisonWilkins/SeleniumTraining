package main.java.com.mycompany.app;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class App 
{
	public static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public static void checkAlert(WebDriver driver, String input) {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, 2);
	        wait.until(ExpectedConditions.alertIsPresent());
	        Alert alert = driver.switchTo().alert();
	        String[] text = alert.getText().split(" ");
	        if (text.length == 2)
	        {
	        	if (input.equals(""))
		        	System.out.println("Length was correct for blank string");
	        	else
		        	System.out.printf("Length was incorrect for blank string\n");  
	        	alert.accept();
	        }
	        else 
	        {
		        int size = Integer.parseInt(text[text.length - 1]);
		        if (size == input.length())
		        	System.out.printf("Length was correct for %s\n", input);
		        else
		        	System.out.printf("Length was incorrect for string %s: expected %d, got %d.\n", input, input.length(), size);
		        alert.accept();
	        }
	        boolean ready = false;
	        while (!ready) {
		        try{
		            driver.switchTo().alert();
		            ready = false;
		        } catch (NoAlertPresentException e){
		            ready = true;
		        }
	        }
	        driver.switchTo().defaultContent();
	    } catch (Exception e) {
	        //exception handling
	    }
	}
	
	public static boolean test(WebElement click, WebElement output, String ex)
	{
		click.submit();
		if (ex.equalsIgnoreCase(output.getText())) {
			System.out.printf("Test :%s: passed\n", ex);
			return true;
		}
		else {
			System.out.printf("Test :%s: failed: got :%s:\n", ex, output.getText());
			return false;
		}
	}
	
    public static void main( String[] args )
    {
    	System.setProperty("webdriver.gecko.driver", "C:\\Users\\mwilkins\\Downloads\\geckodriver-v0.21.0-win64\\geckodriver.exe");
    	WebDriver driver = new FirefoxDriver();
    	driver.get("file:///C:/Users/mwilkins/Documents/Selenium/First/my-app/SeleniumTraining/index.html");
		WebElement inputBar = driver.findElement(By.id("bar"));
		WebElement radio1 = driver.findElement(By.id("r1"));
		WebElement radio2 = driver.findElement(By.id("r2"));
		WebElement box1 = driver.findElement(By.name("phrase1"));
		WebElement box2 = driver.findElement(By.name("phrase2"));
		WebElement output = driver.findElement(By.id("text"));
		Select drop = new Select(driver.findElement(By.name("location")));
    	String input = "people";
		inputBar.sendKeys(input);
    	radio2.click(); // stays on page
    	// nothing selected
    	test(inputBar, output, input);
    	// phrase 1 selected
    	box1.click();
    	test(inputBar, output, "tell me about " + input);
    	// phrase 2 selected
    	box1.click();
    	box2.click();
    	drop.selectByVisibleText("Blacksburg");
    	test(inputBar, output, input + " in blacksburg");
    	drop.selectByVisibleText("Arlington");
    	test(inputBar, output, input + " in arlington");
    	drop.selectByVisibleText("Wilmington");
    	test(inputBar, output, input + " in wilmington");
    	// both phrases selected
    	box1.click();
    	drop.selectByVisibleText("Blacksburg");
    	test(inputBar, output, "tell me about " + input + " in blacksburg");
    	drop.selectByVisibleText("Arlington");
    	test(inputBar, output, "tell me about " + input + " in arlington");
    	drop.selectByVisibleText("Wilmington");
    	test(inputBar, output, "tell me about " + input + " in wilmington");
    	// add redirect
    	radio1.click();
    	inputBar.submit();
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logo")));
		if (driver.getCurrentUrl().contains("google.com/search?q="))
		    System.out.println("Redirect works");
		else
		    System.out.println(driver.getCurrentUrl());
    }
}
