package com.bnr.oms.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public abstract class OrderEvent {
  public String orderId;

  public abstract String getEventType();
}
