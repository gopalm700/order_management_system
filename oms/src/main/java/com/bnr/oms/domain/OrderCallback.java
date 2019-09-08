package com.bnr.oms.domain;

import com.bnr.oms.persistence.entity.Order.OrderStatus;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderCallback {

  @NotBlank
  private String orderId;
  @NotNull
  private OrderStatus status;

}
