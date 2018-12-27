package com.jerryxie.redfin;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.jerryxie.redfin.domain.Region;
import com.jerryxie.redfin.worker.CSVRemoteFileParser;
import com.jerryxie.redfin.worker.UnhandleredRegionReader;

@Component
public class WorkCenter {

    @Autowired
    ApplicationContext context;

    @Autowired
    ScheduledThreadPoolExecutor threadPool;

    private Logger logger = Logger.getLogger(WorkCenter.class);

    private BlockingQueue<Region> unprocessedRegionQueue;

    private CountDownLatch lock;
    private final int parserThreadNum = 100;

    public BlockingQueue<Region> getUnprocessedRegionQueue() {
        return unprocessedRegionQueue;
    }

    public WorkCenter() {
        this.lock = new CountDownLatch(1);
        this.unprocessedRegionQueue = new LinkedBlockingQueue<>(5000);
    }

    public void initWorker() {
   //   threadPool.submit(context.getBean(RegionIDInfoFatcher.class));
        threadPool.submit(context.getBean(UnhandleredRegionReader.class));
        for (int i = 0; i < this.parserThreadNum; i++) {
            threadPool.scheduleWithFixedDelay(context.getBean(CSVRemoteFileParser.class), 30, 1, TimeUnit.SECONDS);
        }
    }

    public CountDownLatch getLock() {
        return lock;
    }
}
