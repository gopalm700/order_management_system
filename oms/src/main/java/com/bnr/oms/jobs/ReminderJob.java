package com.bnr.oms.jobs;

import static com.bnr.oms.persistence.entity.Order.OrderStatus.IN_PROGRESS;
import static com.bnr.oms.persistence.entity.Order.OrderStatus.ORDER_REMINDED;
import static java.util.Calendar.MINUTE;

import com.bnr.oms.events.OrderReminder;
import com.bnr.oms.persistence.entity.Order;
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
public class ReminderJob {

  private Logger logger = LoggerFactory.getLogger(OrderEscalationJob.class);

  @Autowired
  private OrderRepository repository;

  @Autowired
  private OrchestrationService orchestrationService;

  @Scheduled(fixedDelay = 20000)
  public void reminder() {
    logger.info("Reminder Job running.");
    boolean success;
    byte count = 0;
    do {
      count++;
      success = reminderToResturant();
    } while (!success && count < 3);
  }

  @Transactional
  private boolean reminderToResturant() {
    Date currentDate = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(currentDate);
    cal.add(MINUTE, -2);
    try {
      List<Order> orders = repository
          .findAllByStatusAndNotifyTimeBeforeOrderByNotifyTimeAsc(IN_PROGRESS, cal.getTime());
      if (!CollectionUtils.isEmpty(orders)) {
        logger.info(
            "Sending reminders for order ids " + orders.stream().map(o -> o.getId()).collect(
                Collectors.toList()));
        orders.forEach(
            order -> orchestrationService.orchestrate(new OrderReminder(order.getId())));
        List<Order> escaletedOrders = orders.stream().map(e -> {
          e.setStatus(ORDER_REMINDED);
          return e;
        }).collect(Collectors.toList());
        repository.saveAll(escaletedOrders);
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
