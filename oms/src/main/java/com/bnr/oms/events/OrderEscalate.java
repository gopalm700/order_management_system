package com.bnr.oms.events;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEscalate extends OrderEvent {

  public OrderEscalate(String orderId) {
    super(orderId);
  }

  @Override
  public String getEventType() {
    return "ORDER-ESCALETED";
  }
}
