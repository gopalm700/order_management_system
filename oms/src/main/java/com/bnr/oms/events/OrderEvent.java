package com.bnr.oms.events;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class OrderEvent {

  public String orderId;

  public OrderEvent(String orderId) {
    this.orderId = orderId;
  }

  public abstract EventType getEventType();
}
