package com.jerryxie.redfin.worker;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jerryxie.redfin.WorkCenter;
import com.jerryxie.redfin.domain.Region;
import com.jerryxie.redfin.service.RegionService;

@Component
@Scope("prototype")
public class UnhandleredRegionReader implements Runnable {

    @Autowired
    RegionService regionService;

    @Autowired
    ApplicationContext context;

    private Logger logger = Logger.getLogger(UnhandleredRegionReader.class);
    @Override
    public void run() {
        BlockingQueue<Region> bq = context.getBean(WorkCenter.class).getUnprocessedRegionQueue();
       
        List<Region> unprocessedRegions = this.regionService.getUnprocessedRegions();
        unprocessedRegions.forEach(region ->{
            try {
                bq.put(region);
            } catch (InterruptedException e) {
                logger.error(e);
            }
        });
        logger.info(String.format("Currently there is %d pending regions", bq.size()));
      //  this.regionService.markAsProcessed(unprocessedRegions);
    }

}
