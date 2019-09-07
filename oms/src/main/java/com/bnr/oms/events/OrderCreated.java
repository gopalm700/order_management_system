package com.bnr.oms.events;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderCreated extends OrderEvent {

  private Date deliveryTime;
  private Integer quantity;

  public OrderCreated(String orderId, Date deliveryTime, Integer quantity) {
    super(orderId);
    this.deliveryTime = deliveryTime;
    this.quantity = quantity;
  }

  @Override
  public String getEventType() {
    return "ORDER-CREATED";
  }
}
