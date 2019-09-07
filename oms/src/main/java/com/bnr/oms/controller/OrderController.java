package com.bnr.oms.controller;

import com.bnr.oms.domain.OrderDto;
import com.bnr.oms.domain.OrderInfo;
import com.bnr.oms.events.OrderCreated;
import com.bnr.oms.workflow.OrchestrationService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/")
public class OrderController {

  @Autowired
  private OrchestrationService service;

  @PostMapping(value = "/order", consumes = "application/json", produces = "application/json")
  public OrderInfo createOrder(@RequestBody @Valid OrderDto order) {
    service.orchestrate(
        new OrderCreated(order.getOrderId(), order.getOrderTime(), order.getQuantity()));
    return new OrderInfo(order.getOrderId());
  }

}
