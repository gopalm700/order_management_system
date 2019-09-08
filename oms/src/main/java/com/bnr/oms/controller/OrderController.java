package com.bnr.oms.controller;

import com.bnr.oms.domain.OrderDto;
import com.bnr.oms.domain.OrderInfo;
import com.bnr.oms.events.OrderCreated;
import com.bnr.oms.workflow.OrchestrationService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

  private OrchestrationService service;

  @Autowired
  public OrderController(OrchestrationService service) {
    this.service = service;
  }

  @PostMapping(consumes = "application/json", produces = "application/json")
  public OrderInfo createOrder(@RequestBody @Valid OrderDto order) {
    service.orchestrate(
        new OrderCreated(order.getOrderId(), order.getOrderTime(), order.getQuantity()));
    return new OrderInfo(order.getOrderId());
  }

  @PostMapping(path = "/bulk", consumes = "application/json", produces = "application/json")
  public List<OrderInfo> createOrders(@RequestBody @Valid @NotEmpty List<OrderDto> orders) {
    return orders.stream()
        .map(order -> {
              service.orchestrate(
                  new OrderCreated(order.getOrderId(), order.getOrderTime(), order.getQuantity()));
              return new OrderInfo(order.getOrderId());
            }
        ).collect(Collectors.toList());
  }
}
