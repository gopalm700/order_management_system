package com.bnr.oms.events;

import com.bnr.oms.persistence.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderClose extends OrderEvent {

  private OrderStatus status;

  public OrderClose(String orderId, OrderStatus status) {
    super(orderId);
    this.status=status;
  }

  @Override
  public String getEventType() {
    return "ORDER-CLOSE";
  }
}
