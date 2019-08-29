package edu.udacity.java.nano;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class seleniumTests {
        static Logger logger = Logger.getLogger(seleniumTests.class.getName());

        @Test
        public  void main() {
                     //
                     // these test require the app to be running
                     //
                     logger.log(Level.INFO, "Starting selenium test..");
                
                     WebDriver FrodoDriver = new HtmlUnitDriver(true);
                     WebDriver SamDriver = new HtmlUnitDriver(true);
                     FrodoDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
                     FrodoDriver.get("http://localhost:8080/index?username=Frodo");
                     // Locate text input				
                     WebElement element = FrodoDriver.findElement(By.id("msg"));	
                     // Locate send button
                     WebElement sendButton = FrodoDriver.findElement(By.id("sendButton"));
                    // Enter message		
                    element.sendKeys("Hello, Sam.");	
                    // Send message
                    sendButton.click();
                    // Need to wait for the correct page ot load
                    WebDriverWait wait = new WebDriverWait(FrodoDriver, 10);
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(.,'Hello, Sam.')]")));
                    // Okay, either failed (timeout) or we are ready
                    // Cache the pagesource
                    String pageSource = FrodoDriver.getPageSource();
                    // Login test
                    logger.log(Level.INFO, "Starting login test...");
                    assertTrue(pageSource.contains("Frodo：Joined the chat"));
                    // Send message(chat) test
                    logger.log(Level.INFO, "Starting send message(chat) test..");
                    assertTrue(pageSource.contains("Frodo：Hello, Sam."));
                    //
                    // It's all over but the login' out
                    logger.log(Level.INFO, "Starting logout test..");
                    WebElement logoutButton = FrodoDriver.findElement(By.id("logoutButton"));
                    // Retreive Sam's version of the page and wait for update
                    SamDriver.get("http://localhost:8080/index?username=Sam");
                    // log out Frodo
                    logoutButton.click();
                    WebDriverWait waitForLogout = new WebDriverWait(SamDriver, 10);
                    waitForLogout.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(.,'Frodo：Left the chat')]")));
                    // See ya Frodo, have fun storming the volcanoe
                    assertTrue(SamDriver.getPageSource().contains("Frodo：Left the chat"));
                    SamDriver.quit();			
         }		
}
