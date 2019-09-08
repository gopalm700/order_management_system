package com.bnr.oms.controller;

import com.bnr.oms.domain.OrderCallback;
import com.bnr.oms.events.OrderCloseEvent;
import com.bnr.oms.registry.ServiceRegistry;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ResturantCallbackController {

  private ServiceRegistry registry;

  @Autowired
  public ResturantCallbackController(ServiceRegistry registry) {
    this.registry = registry;
  }

  @PostMapping(path = "/callback", consumes = "application/json")
  public void updateStatus(@RequestBody @Valid OrderCallback callback) {
    OrderCloseEvent closeEvent = new OrderCloseEvent(callback.getOrderId(), callback.getStatus());
    registry.find(closeEvent.getEventType()).forEach(
        notificator -> notificator.notify(closeEvent)
    );
  }
}
