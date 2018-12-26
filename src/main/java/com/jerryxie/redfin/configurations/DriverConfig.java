package com.jerryxie.redfin.configurations;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverConfig {

    private final int driverNum = 2;
    @Bean
    public DriverFactory getWebDriverFactory() {
        System.setProperty("webdriver.chrome.driver", getDrivePath());
        DriverFactory factory = new DriverFactory();
        for(int i=0;i<this.driverNum;i++) {
            WebDriver driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            factory.addWebDriver(driver);
        }
        return factory;
    }

    private String getOsName() {
        return System.getProperty("os.name");
    }
    
    private String getDrivePath() {
        String osVersion = getOsName();
        if(osVersion.startsWith("Mac")) {
            return "src/main/resources/browers/chromedriver_mac";
        }
        return "src/main/resources/browers/chromedriver_linux";
    }
    
}
