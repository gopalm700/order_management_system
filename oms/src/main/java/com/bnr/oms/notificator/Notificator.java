package com.bnr.oms.notificator;

import com.bnr.oms.events.OrderEvent;
import com.bnr.oms.events.EventType;

public interface Notificator {
  void notify(OrderEvent event);
  boolean supports(EventType eventType);
}
