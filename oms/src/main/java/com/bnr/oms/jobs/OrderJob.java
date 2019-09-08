package com.bnr.oms.jobs;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

public abstract class OrderJob {

  private static final Logger logger = LoggerFactory.getLogger(OrderJob.class);

  @Transactional
  final void runInTxn(Callable<Boolean> callable) throws Exception {
    int count = 0;
    boolean success;
    do {
      count++;
      success = callable.call();
      delay();
    } while (count < 3 && !success);
  }

  final void delay() {
    try {
      TimeUnit.SECONDS.sleep(10);
    } catch (InterruptedException e) {
      logger.error("Error occurred in initial delay ", e);
    }
  }
}
