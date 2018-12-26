package com.jerryxie.redfin.worker;

import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jerryxie.redfin.configurations.DriverFactory;

@Component
@Scope("prototype")
public class HomeInfoFatcher implements Callable<String> {

    @Autowired
    DriverFactory driverFactory;
    private String zip;
    private final int waitSecs = 3;
    private Logger logger = Logger.getLogger(HomeInfoFatcher.class);

    public HomeInfoFatcher(String zipcode) {
        this.zip = zipcode;
    }

    @Override
    public String call() throws Exception {
        WebDriver driver = null;
        try {
            driver = driverFactory.getNextAvailableDriver();
            return getSourceApi(driver);
        } finally {
            if (driver != null) {
                driverFactory.releaseDriver(driver);
            }
        }

    }

    public String getSourceApi(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, waitSecs);
        String url = getUrl();
        driver.get(url);
        String source = "";
        try {
            List<WebElement> elems = wait
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("download-and-save")));
            if (elems.size() > 0) {
                source = elems.get(0).getAttribute("href").toString();
            }
            source = source.replace("num_homes=350", "num_homes=1000");
            return source;
        } catch (org.openqa.selenium.NoSuchElementException |org.openqa.selenium.TimeoutException ex) {
            return source;
        }

    }

    private String getUrl() {
        return String.format(
                "https://www.redfin.com/zipcode/%s/filter/include=forsale+mlsfsbo+construction+fsbo+sold-1yr", zip);
    }

}
