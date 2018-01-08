package com.github.karalis.selenium;

import com.github.karalis.selenium.test.rule.BrowserEnabled;
import com.github.karalis.selenium.test.rule.SeleniumIT;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by c.karalis on 4/26/2016.
 */
public class CustomSeleniumIT extends SeleniumIT {

    @Rule
    public BrowserEnabled browserEnabled = new BrowserEnabled();


    protected Wait wait;


    @Test
    @BrowserEnabled.Chrome()
    public void testIE()
            throws IOException {
        testCodesCrud(driver);
    }

    @Before()
    public void init() {
        wait = new WebDriverWait(driver, 15).pollingEvery(200,TimeUnit.MILLISECONDS);
    }

    public void testCodesCrud(WebDriver driver) {
        driver.get("http://www.newsbeast.gr");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("ΠΟΛΙΤΙΚΗ")));



        driver.findElement(By.partialLinkText("ΠΟΛΙΤΙΚΗ")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("ΠΟΛΙΤΙΚΗ")));
    }

}
