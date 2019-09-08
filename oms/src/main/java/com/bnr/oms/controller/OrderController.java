package com.bnr.oms.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.bnr.oms.domain.BulkOrderDto;
import com.bnr.oms.domain.OrderDto;
import com.bnr.oms.domain.OrderResponse;
import com.bnr.oms.events.OrderCreatedEvent;
import com.bnr.oms.workflow.OrchestrationService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  private OrchestrationService service;

  @Autowired
  public OrderController(OrchestrationService service) {
    this.service = service;
  }


  @ResponseStatus(CREATED)
  @PostMapping(consumes = "application/json", produces = "application/json")
  public OrderResponse createOrder(@RequestBody @Valid OrderDto orderDto) {
    service.orchestrate(
        new OrderCreatedEvent(orderDto.getOrderId(), orderDto.getOrderTime(),
            orderDto.getQuantity()));
    return new OrderResponse(orderDto.getOrderId());
  }

  @ResponseStatus(CREATED)
  @PostMapping(path = "/bulk", consumes = "application/json", produces = "application/json")
  public List<OrderResponse> createOrders(@RequestBody @Valid BulkOrderDto bulkOrderDto) {
    return bulkOrderDto.getOrders().stream()
        .map(order -> {
              service.orchestrate(
                  new OrderCreatedEvent(order.getOrderId(), order.getOrderTime(), order.getQuantity()));
              return new OrderResponse(order.getOrderId());
            }
        ).collect(Collectors.toList());
  }
}
