package com.bnr.oms.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrderNotExistsException extends RuntimeException {

  private String orderId;

  public OrderNotExistsException(String orderId) {
    this.orderId = orderId;
  }
  public OrderNotExistsException(String orderId, String message) {
    super(message);
    this.orderId = orderId;
  }
}
