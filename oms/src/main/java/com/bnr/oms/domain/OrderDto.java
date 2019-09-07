package com.bnr.oms.domain;


import java.util.Date;
import javax.validation.constraints.Min;
import lombok.Data;
import lombok.Generated;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
public class OrderDto {

  @Generated
  private String orderId;

  @Min(1)
  private Integer quantity;

  @DateTimeFormat(iso = ISO.DATE_TIME)
  private Date orderTime;
}
