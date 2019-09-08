package com.bnr.oms.events;

import static com.bnr.oms.events.EventType.ORDER_NOTIFIED;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderNotifyEvent extends OrderEvent {

  private Date deliveryTime;
  private Integer quantity;

  public OrderNotifyEvent(String orderId, Date deliveryTime, Integer quantity) {
    super(orderId);
    this.deliveryTime = deliveryTime;
    this.quantity = quantity;
  }

  @Override
  public EventType getEventType() {
    return ORDER_NOTIFIED;
  }
}
