package com.bnr.oms.messaging.dto;


import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QueueMessage {

  private String orderId;

  private Date deliveryTime;

  private String action;

  private Integer quantity;

  private String callback;

}
