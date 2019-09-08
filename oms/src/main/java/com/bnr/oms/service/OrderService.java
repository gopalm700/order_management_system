package com.bnr.oms.service;

import com.bnr.oms.domain.OrderResponse;
import com.bnr.oms.persistence.entity.Order;

public interface OrderService {

  OrderResponse createOrder(Order order);

}
