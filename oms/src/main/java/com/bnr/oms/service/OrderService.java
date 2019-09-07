package com.bnr.oms.service;

import com.bnr.oms.domain.OrderInfo;
import com.bnr.oms.persistence.entity.Order;

public interface OrderService {

  OrderInfo createOrder(Order order);

}
