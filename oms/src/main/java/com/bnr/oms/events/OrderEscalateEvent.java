package com.bnr.oms.events;

import static com.bnr.oms.events.EventType.ORDER_ESCALATED;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEscalateEvent extends OrderEvent {

  public OrderEscalateEvent(String orderId) {
    super(orderId);
  }

  @Override
  public EventType getEventType() {
    return ORDER_ESCALATED;
  }
}
