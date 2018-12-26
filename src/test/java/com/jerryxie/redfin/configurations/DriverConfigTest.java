package com.jerryxie.redfin.configurations;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class DriverConfigTest {

    @Test
    public void test() {
        DriverConfig factory = new DriverConfig();
        WebDriver driver = factory.getWebDriverFactory().getNextAvailableDriver();
        driver.get("https://www.redfin.com/zipcode/95132/filter/include=forsale+mlsfsbo+construction+fsbo+sold-1yr");
        System.out.println(driver.getPageSource());
        driver.close();
    }

}
