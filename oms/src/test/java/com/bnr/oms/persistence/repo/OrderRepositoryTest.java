package com.bnr.oms.persistence.repo;

import static org.assertj.core.api.Assertions.assertThat;

import com.bnr.oms.persistence.entity.Order;
import com.bnr.oms.persistence.entity.Order.OrderStatus;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderRepositoryTest {

  @Autowired
  private OrderRepository repository;

  @Test
  public void injectedComponentsAreNotNull() {
    assertThat(repository).isNotNull();
  }

  @Test
  public void testExpectedOrders() {
    repository.save(
        Order.builder().status(OrderStatus.NEW)
            .deliveryTime(new Date())
            .notifyTime(new Date())
            .id("1234")
            .orderDetails(1)
            .build()
    );
    List<Order> orders = repository
        .findAllByStatusAndNotifyTimeBeforeOrderByNotifyTimeAsc(OrderStatus.NEW,
            new Date(Instant.now().plus(1,
                ChronoUnit.MINUTES).toEpochMilli()));
    assertThat(orders.size()).isEqualTo(1);
    repository.deleteAll();
  }
}
