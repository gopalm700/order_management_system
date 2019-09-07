package com.bnr.oms.persistence.repo;

import com.bnr.oms.persistence.entity.Order;
import com.bnr.oms.persistence.entity.OrderStatus;
import java.util.Date;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, String> {

  List<Order> findAllByStatusAndNotifyTimeBetween(OrderStatus status, Date startTime,
      Date endTime);

  List<Order> findAllByStatusAndNotifyTimeBefore(OrderStatus status, Date time);
}
