package com.jerryxie.redfin;

import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.jerryxie.redfin.worker.RegionIDInfoFatcher;

@Component
public class WorkerCenter {

	@Autowired
	ApplicationContext context;

	@Autowired
	ScheduledThreadPoolExecutor threadPool;

	private Logger logger = Logger.getLogger(WorkerCenter.class);

	public void initWorker() {
		threadPool.submit(context.getBean(RegionIDInfoFatcher.class));
	}
}
