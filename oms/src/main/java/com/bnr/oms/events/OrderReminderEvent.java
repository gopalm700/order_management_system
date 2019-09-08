package com.bnr.oms.events;

import static com.bnr.oms.events.EventType.ORDER_REMINDER;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderReminderEvent extends OrderEvent {
  public OrderReminderEvent(String orderId) {
    super(orderId);
  }

  @Override
  public EventType getEventType() {
    return ORDER_REMINDER;
  }
}
