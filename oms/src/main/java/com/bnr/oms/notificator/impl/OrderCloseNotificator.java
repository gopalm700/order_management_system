package com.bnr.oms.notificator.impl;

import com.bnr.oms.events.OrderClose;
import com.bnr.oms.events.OrderEvent;
import com.bnr.oms.notificator.Notificator;
import com.bnr.oms.persistence.entity.Order;
import com.bnr.oms.persistence.repo.OrderRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderCloseNotificator implements Notificator {


  @Autowired
  private OrderRepository repository;

  @Override
  @Transactional
  public void notify(OrderEvent event) {
    OrderClose closeEvent = (OrderClose) event;
    Optional<Order> order = repository.findById(event.getOrderId());
    order.ifPresent(
        o -> {
          o.setStatus(closeEvent.getStatus());
          repository.save(o);
        }
    );

  }

  @Override
  public boolean supports(String eventType) {
    return "ORDER-CLOSE".equals(eventType);
  }
}
