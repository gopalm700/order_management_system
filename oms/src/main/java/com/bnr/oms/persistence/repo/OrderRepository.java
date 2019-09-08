package com.bnr.oms.persistence.repo;

import com.bnr.oms.persistence.entity.Order;
import com.bnr.oms.persistence.entity.Order.OrderStatus;
import java.util.Date;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, String> {

  List<Order> findAllByStatusAndNotifyTimeBeforeOrderByNotifyTimeAsc(final OrderStatus status,
      final Date time);
}
