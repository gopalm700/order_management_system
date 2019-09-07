package com.bnr.oms.events;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderNotify extends OrderEvent {

  public OrderNotify(String orderId) {
    super(orderId);
  }

  @Override
  public String getEventType() {
    return "ORDER-NOTIFIED";
  }
}
