package com.neurocom.tariff.selenium.test.rule;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.Platform;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Christos Karalis on 30/9/2014.
 */
public class BrowserEnabled implements MethodRule {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public @interface InternetExplorer {
        String version() default "8";
        Platform platform() default Platform.WINDOWS;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public @interface Repeat {
        int value() default 1;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public @interface Firefox {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public @interface Chrome {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public @interface CustomBrowser {}

    public Statement apply(final Statement base, FrameworkMethod method, Object target) {
        if ((method.getAnnotation(InternetExplorer.class)!=null
                && StringUtils.equals(System.getProperties().getProperty("maven.skip.tests.ie"), "true"))
                || (method.getAnnotation(Firefox.class)!=null
                && StringUtils.equals(System.getProperties().getProperty("maven.skip.tests.firefox"), "true"))
                || (method.getAnnotation(Chrome.class)!=null
                && StringUtils.equals(System.getProperties().getProperty("maven.skip.tests.chrome"), "true"))) {

            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    Assume.assumeFalse(true);
                }
            };
        } else {
            if (target instanceof SeleniumIT) {
                try {
                    if (method.getAnnotation(InternetExplorer.class)!=null) {
                        DesiredCapabilities internetExplorer = DesiredCapabilities.internetExplorer();
                        internetExplorer.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                        internetExplorer.setVersion(method.getAnnotation(InternetExplorer.class).version());
                        internetExplorer.setPlatform(method.getAnnotation(InternetExplorer.class).platform());
                        ((SeleniumIT) target).setDriver(new RemoteWebDriver(
                                new URL(SeleniumIT.SELENIUM_HUB_URL), internetExplorer));
                    } else if (method.getAnnotation(Firefox.class)!=null) {
                        ((SeleniumIT) target).setDriver(new RemoteWebDriver(
                                new URL(SeleniumIT.SELENIUM_HUB_URL), SeleniumIT.firefox32));
                    } else if (method.getAnnotation(Chrome.class)!=null) {
                        ((SeleniumIT) target).setDriver(new RemoteWebDriver(
                                new URL(SeleniumIT.SELENIUM_HUB_URL), SeleniumIT.chrome));
                    } else {
                        Assert.assertTrue("Unexpected type of test", false);
                    }
                } catch (MalformedURLException e) {
                    Assert.assertTrue("Malformed hub url", false);
                }
                return new Statement() {
                    @Override
                    public void evaluate() throws Throwable {
                        base.evaluate();
                    }
                };
            } else {
                return new Statement() {
                    @Override
                    public void evaluate() throws Throwable {
                        Assert.assertTrue("Unexpected selenium test", false);
                    }
                };
            }

        }
    }

}