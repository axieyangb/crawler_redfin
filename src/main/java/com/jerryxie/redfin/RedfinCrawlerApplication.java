package com.jerryxie.redfin;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RedfinCrawlerApplication {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        ApplicationContext context = SpringApplication.run(RedfinCrawlerApplication.class, args);
        context.getBean(WorkCenter.class).initWorker();
    }
}
