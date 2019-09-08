package com.bnr.oms.jobs;

import static com.bnr.oms.persistence.entity.Order.OrderStatus.NEW;
import static com.bnr.oms.persistence.entity.Order.OrderStatus.IN_PROGRESS;
import static java.util.Calendar.MINUTE;

import com.bnr.oms.events.OrderNotify;
import com.bnr.oms.persistence.entity.Order;
import com.bnr.oms.persistence.repo.OrderRepository;
import com.bnr.oms.workflow.OrchestrationService;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.OptimisticLockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class SendOrderToResturantJob extends OrderJob{

  private final Logger logger = LoggerFactory.getLogger(SendOrderToResturantJob.class);

  private OrchestrationService orchestrationService;

  private OrderRepository repository;

  @Autowired
  public SendOrderToResturantJob(OrderRepository repository, OrchestrationService orchestrationService) {
    this.repository = repository;
    this.orchestrationService = orchestrationService;
  }

  @Scheduled(fixedDelay = 5000)
  public void notifyEligibleOrders() {
    try {
      runInTxn(() -> sendOrders());
    } catch (Exception e) {
      logger.error("Error occurred while sending Order to restaurant", e);
    }
  }

  private boolean sendOrders() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(MINUTE, 1);
    try {
      List<Order> orders = repository
          .findAllByStatusAndNotifyTimeBeforeOrderByNotifyTimeAsc(NEW, cal.getTime());
      if (!CollectionUtils.isEmpty(orders)) {

        logger.info("Sending orders to restaurant");

        orders.stream()
            .map(o -> new OrderNotify(o.getId(), o.getDeliveryTime(), o.getOrderDetails()))
            .forEach(orderNotify -> orchestrationService.orchestrate(orderNotify));

        List<Order> inProgresOrders = orders.stream()
            .map(order -> order.updateStatus(IN_PROGRESS))
            .collect(Collectors.toList());

        repository.saveAll(inProgresOrders);
      }
    } catch (final OptimisticLockException e) {
      return false;
    }
    return true;
  }
}
