package com.neurocom.tariff.selenium.test.rule;

import com.thoughtworks.selenium.SeleneseTestBase;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.BeforeClass;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by Christos Karalis on 16/9/2014.
 */
public class SeleniumIT extends SeleneseTestBase {

    private static final Logger logger =
            Logger.getLogger(SeleniumIT.class);

    public static String SELENIUM_HUB_URL;

    protected WebDriver driver = null;

    public static DesiredCapabilities internetExplorer8 = DesiredCapabilities.internetExplorer();
    public static DesiredCapabilities internetExplorer9 = DesiredCapabilities.internetExplorer();
    public static DesiredCapabilities internetExplorer = DesiredCapabilities.internetExplorer();
    public static DesiredCapabilities firefox32 = DesiredCapabilities.firefox();
    public static DesiredCapabilities chrome = DesiredCapabilities.chrome();

    static {
        internetExplorer8.setVersion("8");
        internetExplorer8.setPlatform(Platform.WIN8);

        internetExplorer9.setVersion("9");
        internetExplorer9.setPlatform(Platform.WINDOWS);

        internetExplorer.setPlatform(Platform.WINDOWS);

        firefox32.setVersion("38.0");
        firefox32.setPlatform(Platform.WINDOWS);

        chrome.setPlatform(Platform.WINDOWS);

    }

    @BeforeClass
    public static void initEnvironment() {
        SELENIUM_HUB_URL =
                getConfigurationProperty(
                "SELENIUM_HUB_URL",
                "test.selenium.hub.url",
                "http://localhost:4444/wd/hub");
        logger.info("using Selenium hub at: " + SELENIUM_HUB_URL);
    }

    private static String getConfigurationProperty(
            String envKey, String sysKey, String defValue) {
        String retValue = defValue;
        String envValue = System.getenv(envKey);
        String sysValue = System.getProperty(sysKey);
        // system property prevails over environment variable
        if (sysValue != null) {
            retValue = sysValue;
        } else if (envValue != null) {
            retValue = envValue;
        }
        return retValue;
    }


    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    @After
    public void destroy() {
        driver.quit();
    }

}
