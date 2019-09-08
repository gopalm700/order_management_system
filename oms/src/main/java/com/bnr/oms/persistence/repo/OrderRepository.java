package com.bnr.oms.persistence.repo;

import com.bnr.oms.persistence.entity.Order;
import com.bnr.oms.persistence.entity.Order.OrderStatus;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, String> {

  @Lock(value = LockModeType.OPTIMISTIC)
  List<Order> findAllByStatusAndNotifyTimeBeforeOrderByNotifyTimeAsc(final OrderStatus status,
      final Date time);

  @Lock(value = LockModeType.OPTIMISTIC)
  Optional<Order> findById(String id);
}
