package com.bnr.oms.controller;

import com.bnr.oms.domain.OrderCallback;
import com.bnr.oms.events.OrderClose;
import com.bnr.oms.notificator.NotificatorFactory;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class CallbackController {

  @Autowired
  private NotificatorFactory factory;

  @PostMapping("/callback")
  public void updateStatus(@RequestBody @Valid OrderCallback callback) {
    OrderClose closeEvent = new OrderClose(callback.getOrderId(), callback.getStatus());
    factory.find(closeEvent.getEventType()).forEach(
        notificator -> notificator.notify(closeEvent)
    );
  }
}
