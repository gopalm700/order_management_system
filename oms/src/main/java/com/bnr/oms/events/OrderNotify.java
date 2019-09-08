package com.bnr.oms.events;

import static com.bnr.oms.events.EventType.ORDER_NOTIFIED;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderNotify extends OrderEvent {

  private Date deliveryTime;
  private Integer quantity;

  public OrderNotify(String orderId, Date deliveryTime, Integer quantity) {
    super(orderId);
    this.deliveryTime = deliveryTime;
    this.quantity = quantity;
  }

  @Override
  public EventType getEventType() {
    return ORDER_NOTIFIED;
  }
}
