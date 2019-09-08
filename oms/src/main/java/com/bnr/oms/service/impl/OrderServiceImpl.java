package com.bnr.oms.service.impl;

import com.bnr.oms.domain.OrderResponse;
import com.bnr.oms.persistence.entity.Order;
import com.bnr.oms.persistence.repo.OrderRepository;
import com.bnr.oms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

  private OrderRepository repository;

  @Autowired
  public OrderServiceImpl(OrderRepository repository){
    this.repository = repository;
  }

  @Override
  @Transactional
  public OrderResponse createOrder(Order order) {
    repository.save(order);
    return new OrderResponse(order.getId());
  }


}

