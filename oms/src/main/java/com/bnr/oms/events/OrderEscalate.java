package com.bnr.oms.events;

import static com.bnr.oms.events.EventType.ORDER_ESCALATED;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEscalate extends OrderEvent {

  public OrderEscalate(String orderId) {
    super(orderId);
  }

  @Override
  public EventType getEventType() {
    return ORDER_ESCALATED;
  }
}
