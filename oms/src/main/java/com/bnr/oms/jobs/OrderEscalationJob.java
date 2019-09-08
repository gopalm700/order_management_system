package com.bnr.oms.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderEscalationJob extends OrderJob {

  private Logger logger = LoggerFactory.getLogger(OrderEscalationJob.class);

  @Scheduled(fixedDelay = 60000)
  public void escalate() {
    logger.info("Detecting Anomalies");
  }

}
