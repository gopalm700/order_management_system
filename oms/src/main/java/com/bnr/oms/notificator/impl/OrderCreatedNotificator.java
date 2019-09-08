package com.bnr.oms.notificator.impl;

import static com.bnr.oms.events.EventType.ORDER_CREATED;
import static com.bnr.oms.persistence.entity.Order.OrderStatus.NEW;

import com.bnr.oms.events.OrderCreated;
import com.bnr.oms.events.OrderEvent;
import com.bnr.oms.events.EventType;
import com.bnr.oms.notificator.Notificator;
import com.bnr.oms.persistence.entity.Order;
import com.bnr.oms.persistence.repo.OrderRepository;
import com.bnr.oms.service.OrderService;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedNotificator implements Notificator {

  private OrderService service;

  @Autowired
  public OrderCreatedNotificator(OrderService service) {
    this.service = service;
  }

  @Override
  public void notify(final OrderEvent event) {
    OrderCreated createdEvent = (OrderCreated) event;
    Order order = Order.builder()
        .deliveryTime(createdEvent.getDeliveryTime())
        .notifyTime(
            calculateDeliveryTime(createdEvent.getDeliveryTime(), createdEvent.getQuantity()))
        .id(event.getOrderId())
        .status(NEW)
        .orderDetails(createdEvent.getQuantity()).build();
    service.createOrder(order);
  }

  private Date calculateDeliveryTime(final Date date, final Integer quantity) {
    Integer minutes = (quantity > 1) ? 30 : 15;
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.MINUTE, -minutes);
    return cal.getTime();
  }

  @Override
  public boolean supports(final EventType eventType) {
    return eventType == ORDER_CREATED;
  }
}
