package com.bnr.oms.workflow.impl;

import com.bnr.oms.events.OrderEvent;
import com.bnr.oms.notificator.NotificatorFactory;
import com.bnr.oms.workflow.OrchestrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrchestrationServiceImpl implements OrchestrationService {

  @Autowired
  private NotificatorFactory factory;

  @Override
  public void orchestrate(OrderEvent event) {
    factory.find(event.getEventType()).forEach(
        notificator -> notificator.notify(event)
    );
  }
}
