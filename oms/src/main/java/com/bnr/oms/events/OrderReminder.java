package com.bnr.oms.events;

import static com.bnr.oms.events.EventType.ORDER_REMINDER;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderReminder extends OrderEvent {
  public OrderReminder(String orderId) {
    super(orderId);
  }

  @Override
  public EventType getEventType() {
    return ORDER_REMINDER;
  }
}
