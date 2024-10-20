package com.ar.spinehr.serviceimpl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SeleniumServiceImpl {

    // ChromDriver link = https://chromedriver.storage.googleapis.com/index.html

    // https://chromedriver.storage.googleapis.com/index.html?path=114.0.5735.90/

    // till chrome version 114 chrome driver is required else not required

    public boolean doAction(String username, String password, String category, String action) {

        // Set the path to the chromedriver executable
        // System.setProperty("webdriver.chrome.driver",
        // "/home/a-r-danish/Documents/Software/chromedriver_linux64/chromedriver");


        // Initialize ChromeOptions for headless mode
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");


        // Initialize the ChromeDriver
        // WebDriver driver = new ChromeDriver();
        WebDriver driver = new ChromeDriver(options);

        try {
            // Navigate to the target website
            driver.get("https://germin8.spinehr.in/login.aspx");

            // open in full screen
            // driver.manage().window().maximize();

            // Handle the cookie consent popup
            WebElement acceptCookiesButton = driver.findElement(By.id("btnAccept"));
            if (acceptCookiesButton.isDisplayed()) {
                acceptCookiesButton.click();
            }

            // keep it open for 2 seconds
            waitTime(2);

            if (login(driver, username, password)) {
                log.info("Logged in successfully");
                
                waitTime(2);
                
                if(selectCategory(driver, category)){
                    log.info("Category selected successfully");
                    if(action.equalsIgnoreCase("markIn") || action.equalsIgnoreCase("in")){
                        if(markIn(driver)){
                            log.info("Marked in successfully");
                        }else{
                            log.error("Error while marking in");
                        }
                    }else if(action.equalsIgnoreCase("markOut") || action.equalsIgnoreCase("out")){
                        if(markOut(driver)){
                            log.info("Marked out successfully");
                        }else{
                            log.error("Error while marking out");
                        }
                    }else{
                        log.error("Invalid action");
                    }
                }else{
                    log.error("Error while selecting category");
                }

            } else {
                log.error("Error while logging in");
            }

            // Additional actions can be performed here
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            // log.info("Closing the browser in 15 seconds");
            // waitTime(15);
            log.info("Closing the browser now");
            driver.quit();
        }
        return false;
    }

    public boolean login(WebDriver driver, String username, String password) {

        try {
            WebElement usernameInputBox = driver.findElement(By.cssSelector("input[id='txtUser']"));
            usernameInputBox.sendKeys(username);

            WebElement passwordInputBox = driver.findElement(By.cssSelector("input[id='txtPassword']"));
            passwordInputBox.sendKeys(password);

            WebElement loginButton = driver.findElement(By.id("btnLogin"));
            loginButton.click();

            return true;
        } catch (Exception e) {
            log.error("Error while logging in: {}", e);
        }

        return false;
    }

    public boolean markIn(WebDriver driver) {
        try {
            WebElement markInButton = driver.findElement(By.id("ctl00_BodyContentPlaceHolder_btnMarkIn"));
            markInButton.click();
            return true;
        } catch (Exception e) {
            log.error("Error while marking in: {}", e);
        }
        return false;
    }

    public boolean markOut(WebDriver driver) {
        try {
            WebElement markOutButton = driver.findElement(By.id("ctl00_BodyContentPlaceHolder_btnMarkOut"));
            markOutButton.click();
            return true;
        } catch (Exception e) {
            log.error("Error while marking out: {}", e);
        }
        return false;
    }

    public boolean selectCategory(WebDriver driver, String category) {
        try {
            WebElement dropdownElement = driver.findElement(By.id("ctl00_BodyContentPlaceHolder_drpSwipeCategory"));
            Select dropdown = new Select(dropdownElement);
            dropdown.selectByValue(category);
            return true;
        } catch (Exception e) {
            log.error("Error while selecting category: {}", e);
        }
        return false;
    }

    public boolean goToDashboard(WebDriver driver) {
        try {
            WebElement dashboardButton = driver.findElement(By.id("ctl00_BodyContentPlaceHolder_lnkGoToDashBoard"));
            dashboardButton.click();
            return true;
        } catch (Exception e) {
            log.error("Error while going to dashboard: {}", e);
        }
        return false;
    }

    public static void waitTime(int time){
        // time in seconds
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}