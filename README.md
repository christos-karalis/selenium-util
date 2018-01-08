## Selenium Rule Util Package

A generic util that integrates unit test Selenium WebDriver with a Junit test and through a 
[@org.junit.Rule](http://junit.org/junit4/javadoc/4.11/org/junit/Rule.html) can configure browser 
and its version

e.g. 

```java

public class CustomSeleniumIT extends SeleniumIT {

    /**
    *   A rule that injects and enables web driver
    */
    @Rule
    public BrowserEnabled browserEnabled = new BrowserEnabled();
   

    /**
    *  A ChromeDriver should be initialized through 
    *  {@link com.github.karalis.selenium.test.rule.BrowserEnabled}.
    * The test will be disabled if it is started with -D maven.skip.tests.ie=true
    */
    @Test
    @BrowserEnabled.Chrome()
    public void testIE()
            throws IOException {
        testCodesCrud(driver);
    }


}



```