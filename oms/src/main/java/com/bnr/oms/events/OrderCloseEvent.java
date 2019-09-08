package com.bnr.oms.events;

import static com.bnr.oms.events.EventType.ORDER_CLOSE;

import com.bnr.oms.persistence.entity.Order.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCloseEvent extends OrderEvent {

  private OrderStatus status;

  public OrderCloseEvent(String orderId, OrderStatus status) {
    super(orderId);
    this.status = status;
  }

  @Override
  public EventType getEventType() {
    return ORDER_CLOSE;
  }
}
