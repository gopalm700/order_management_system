package com.bnr.oms.persistence.entity;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_table")
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


  public enum OrderStatus {
    CREATED, IN_PROGRESS, ORDER_REMINDED, ESCALATE_HUMAN, DELIVERED, REJECTED
  }
}


