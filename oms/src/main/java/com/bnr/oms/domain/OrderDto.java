package com.bnr.oms.domain;


import java.util.Date;
import java.util.UUID;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Generated;

@Data
public class OrderDto {

  @Generated
  private String orderId = UUID.randomUUID().toString();

  @Min(1)
  private Integer quantity;

  @NotNull
  private Date orderTime;
}
