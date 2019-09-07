package com.bnr.oms.notificator;

import com.bnr.oms.events.OrderEvent;

public interface Notificator {
  void notify(OrderEvent event);
  boolean supports(String eventType);
}
