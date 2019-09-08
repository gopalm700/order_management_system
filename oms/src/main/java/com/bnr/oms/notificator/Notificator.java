package com.bnr.oms.notificator;

import com.bnr.oms.events.OrderEvent;
import com.bnr.oms.events.EventType;

public interface Notificator {
  void notify(final OrderEvent event);
  boolean supports(final EventType eventType);
}
