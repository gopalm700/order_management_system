package com.bnr.oms.notificator.impl;

import static com.bnr.oms.events.EventType.ORDER_CLOSE;

import com.bnr.oms.events.EventType;
import com.bnr.oms.events.OrderClose;
import com.bnr.oms.events.OrderEvent;
import com.bnr.oms.exception.OrderNotExistsException;
import com.bnr.oms.notificator.Notificator;
import com.bnr.oms.persistence.entity.Order;
import com.bnr.oms.persistence.repo.OrderRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderCloseNotificator implements Notificator {

  private static final Logger logger = LoggerFactory.getLogger(OrderCloseNotificator.class);

  private OrderRepository repository;

  @Autowired
  public OrderCloseNotificator(OrderRepository repository) {
    this.repository = repository;
  }

  @Override
  @Transactional
  public void notify(final OrderEvent event) {
    logger.debug("Closing order for order " + event.getOrderId());
    OrderClose closeEvent = (OrderClose) event;
    Optional<Order> optionalOrder = repository.findById(event.getOrderId());

    if (optionalOrder.isPresent()) {
      Order order = optionalOrder.get();
      order.setStatus(closeEvent.getStatus());
      repository.save(order);
    } else {
      throw new OrderNotExistsException(event.getOrderId());
    }
  }

  @Override
  public boolean supports(final EventType eventType) {
    return eventType == ORDER_CLOSE;
  }
}
