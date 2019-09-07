package com.bnr.oms.service.impl;

import com.bnr.oms.domain.OrderInfo;
import com.bnr.oms.persistence.entity.Order;
import com.bnr.oms.persistence.repo.OrderRepository;
import com.bnr.oms.service.OrderService;
import com.bnr.oms.workflow.OrchestrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderRepository repository;

  @Autowired
  private OrchestrationService orchestrationService;

  @Override
  @Transactional
  public OrderInfo createOrder(Order order) {
    repository.save(order);
    return new OrderInfo(order.getId());
  }


}

