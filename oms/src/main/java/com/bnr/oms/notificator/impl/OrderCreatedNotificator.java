package com.bnr.oms.notificator.impl;

import com.bnr.oms.events.OrderCreated;
import com.bnr.oms.events.OrderEvent;
import com.bnr.oms.notificator.Notificator;
import com.bnr.oms.persistence.entity.Order;
import com.bnr.oms.persistence.entity.OrderStatus;
import com.bnr.oms.service.OrderService;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedNotificator implements Notificator {

  @Autowired
  private OrderService service;

  @Override
  public void notify(OrderEvent event) {
    OrderCreated createdEvent = (OrderCreated) event;
    Order order = Order.builder()
        .deliveryTime(createdEvent.getDeliveryTime())
        .notifyTime(
            calculateDeliveryTime(createdEvent.getDeliveryTime(), createdEvent.getQuantity()))
        .id(event.getOrderId())
        .status(OrderStatus.CREATED)
        .orderDetails(createdEvent.getQuantity()).build();
    service.createOrder(order);
  }

  private Date calculateDeliveryTime(Date date, Integer quantity) {
    Integer minutes = (quantity > 1) ? 30 : 15;
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.MINUTE, -minutes);
    return cal.getTime();
  }

  @Override
  public boolean supports(String eventType) {
    return eventType.equals("ORDER-CREATED");
  }
}
