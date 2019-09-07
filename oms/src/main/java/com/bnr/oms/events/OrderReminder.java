package com.bnr.oms.events;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderReminder extends OrderEvent {
  public OrderReminder(String orderId) {
    super(orderId);
  }

  @Override
  public String getEventType() {
    return "ORDER-REMINDER";
  }
}
