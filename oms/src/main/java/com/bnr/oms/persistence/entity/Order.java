package com.bnr.oms.persistence.entity;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Data
@Builder
public class Order {

  @Id
  private String id;

  @Column(name = "order_details")
  private Integer orderDetails;

  @Column(name = "order_status")
  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @Column(name = "delivery_time")
  @Temporal(TIMESTAMP)
  private Date deliveryTime;

  @Column(name = "notify_time")
  @Temporal(TIMESTAMP)
  private Date notifyTime;

  @CreatedDate
  @Temporal(TIMESTAMP)
  protected Date creationDate;

  @LastModifiedDate
  @Temporal(TIMESTAMP)
  protected Date lastModifiedDate;

  @Version
  private Integer version;
}
