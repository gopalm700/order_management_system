package com.bnr.oms.workflow;

import com.bnr.oms.events.OrderEvent;

public interface OrchestrationService {
  void orchestrate(OrderEvent event);
}
