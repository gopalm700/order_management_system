package com.bnr.oms.workflow.impl;

import com.bnr.oms.events.OrderEvent;
import com.bnr.oms.registry.ServiceRegistry;
import com.bnr.oms.workflow.OrchestrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrchestrationServiceImpl implements OrchestrationService {

  private ServiceRegistry registry;

  @Autowired
  public OrchestrationServiceImpl(ServiceRegistry registry) {
    this.registry = registry;
  }

  @Override
  public void orchestrate(final OrderEvent event) {
    registry.find(event.getEventType())
        .forEach(notificator -> notificator.notify(event));
  }
}
