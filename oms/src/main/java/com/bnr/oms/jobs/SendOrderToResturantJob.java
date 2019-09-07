package com.bnr.oms.jobs;

import static com.bnr.oms.persistence.entity.OrderStatus.CREATED;
import static java.util.Calendar.MINUTE;

import com.bnr.oms.events.OrderNotify;
import com.bnr.oms.persistence.entity.Order;
import com.bnr.oms.persistence.entity.OrderStatus;
import com.bnr.oms.persistence.repo.OrderRepository;
import com.bnr.oms.workflow.OrchestrationService;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.persistence.OptimisticLockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
public class SendOrderToResturantJob {

  private Logger logger = LoggerFactory.getLogger(SendOrderToResturantJob.class);

  @Autowired
  private OrchestrationService orchestrationService;

  @Autowired
  private OrderRepository repository;

  @Scheduled(fixedDelay = 5000)
  public void notifyEligibleOrders() {
    boolean success = false;
    byte count = 0;
    do {
      count++;
      success = sendOrders();
    } while (!success && count < 3);
  }

  @Transactional
  private boolean sendOrders() {
    Date currentDate = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(currentDate);
    cal.add(MINUTE, 1);
    try {
      List<Order> orders = repository
          .findAllByStatusAndNotifyTimeBetween(CREATED, currentDate, cal.getTime());
      if (!CollectionUtils.isEmpty(orders)) {
        logger.info("Sending orders to resturant");
        orders
            .forEach(
                order -> orchestrationService.orchestrate(new OrderNotify(order.getId())));
        List<Order> inprogressOrders = orders.stream().map(e -> {
          e.setStatus(OrderStatus.IN_PROGRESS);
          return e;
        }).collect(Collectors.toList());
        repository.saveAll(inprogressOrders);
      }
    } catch (OptimisticLockException e) {
      try {
        TimeUnit.MINUTES.sleep(1);
      } catch (Exception ex) {
      }
      return false;
    }
    return true;
  }
}
