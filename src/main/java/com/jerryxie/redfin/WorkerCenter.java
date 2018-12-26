package com.jerryxie.redfin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.jerryxie.redfin.helper.BayareaZipCodeUtility;
import com.jerryxie.redfin.worker.CSVRemoteFileParser;
import com.jerryxie.redfin.worker.HomeInfoFatcher;

@Component
public class WorkerCenter {

    @Autowired
    ApplicationContext context;

    @Autowired
    ScheduledThreadPoolExecutor threadPool;

    private Logger logger = Logger.getLogger(WorkerCenter.class);

    public void initWorker() {
        List<String> zipCodes = context.getBean(BayareaZipCodeUtility.class).getZipcodes();
        List<Future<String>> tasks = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        zipCodes.stream().forEach(zipCode -> {
            Future<String> task = this.threadPool.submit(context.getBean(HomeInfoFatcher.class, zipCode));
            tasks.add(task);
        });
        tasks.forEach(task -> {
            try {
                String url = task.get();
                System.out.println("Got URL: " + url);
                urls.add(url);
            } catch (InterruptedException e) {
                logger.error(e);
            } catch (ExecutionException e) {
                logger.error(e);
            }
        });

        CountDownLatch latch = new CountDownLatch(urls.size());
        urls.forEach(url -> {
            this.threadPool.submit(context.getBean(CSVRemoteFileParser.class, url, latch));
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            logger.error(e);
        }

    }
}
