package com.bnr.oms.notificator.impl;

import com.bnr.oms.events.OrderEvent;
import com.bnr.oms.events.OrderNotify;
import com.bnr.oms.notificator.Notificator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ResturantNotificator implements Notificator {

  private static final Logger logger = LoggerFactory.getLogger(ResturantNotificator.class);

  @Override
  public void notify(OrderEvent event) {
    if (event instanceof OrderNotify) {
      logger.info("Notify Resturant " + event.getOrderId());
    } else {
      logger.info("Remind Resturant " + event.getOrderId());
    }

  }

  @Override
  public boolean supports(String eventType) {
    return "ORDER-NOTIFIED".equals(eventType) || "ORDER-REMINDER".equals(eventType);
  }
}
