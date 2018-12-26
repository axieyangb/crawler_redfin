package com.jerryxie.redfin.configurations;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class DriverFactory {
    private BlockingQueue<WebDriver> vacantDrivers;
    private Set<WebDriver> allDrivers;
    private Logger logger = Logger.getLogger(DriverFactory.class);

    public DriverFactory() {
        this.vacantDrivers = new LinkedBlockingQueue<>();
        allDrivers = new HashSet<>();
    }

    public WebDriver getNextAvailableDriver() {
        try {
            WebDriver taken = this.vacantDrivers.take();
            return taken;
        } catch (InterruptedException e) {
            logger.error(e);
            return null;
        }
    }

    public boolean addWebDriver(WebDriver driver) {
        try {
            this.vacantDrivers.put(driver);
            this.allDrivers.add(driver);
            return true;
        } catch (InterruptedException e) {
            logger.error(e);
            return false;
        }
    }

    public void releaseDriver(WebDriver driver) {
        addWebDriver(driver);
    }
    public void shutdown() {
        this.allDrivers.forEach(driver->{
            driver.close();
        });
    }
}
