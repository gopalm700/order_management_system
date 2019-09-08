package com.bnr.oms.jobs;

import static com.bnr.oms.persistence.entity.Order.OrderStatus.IN_PROGRESS;
import static com.bnr.oms.persistence.entity.Order.OrderStatus.ORDER_REMINDED;
import static java.util.Calendar.MINUTE;

import com.bnr.oms.events.OrderReminderEvent;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
public class ReminderJob extends OrderJob {

  private final Logger logger = LoggerFactory.getLogger(ReminderJob.class);

  private OrderRepository repository;

  private OrchestrationService orchestrationService;

  @Autowired
  public ReminderJob(OrderRepository repository, OrchestrationService orchestrationService) {
    this.repository = repository;
    this.orchestrationService = orchestrationService;
  }

  @Scheduled(fixedDelay = 20000)
  @Transactional
  public void reminder() {
    logger.info("Reminder Job running.");
    try {
      runInTxn(() -> reminderToRestaurant());
    } catch (Exception e) {
      logger.error("Error occurred while reminding to restaurant ", e);
    }
  }

  private boolean reminderToRestaurant() {
    Date currentDate = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(currentDate);
    cal.add(MINUTE, -1);
    try {
      List<Order> orders = repository
          .findAllByStatusAndNotifyTimeBeforeOrderByNotifyTimeAsc(IN_PROGRESS, cal.getTime());
      if (!CollectionUtils.isEmpty(orders)) {

        String ids = orders.stream()
            .map(o -> o.getId())
            .reduce("Sending reminders for order ids - ", (a, b) -> (a + " " + b));
        logger.info(ids);

        orders.stream()
            .map(order -> new OrderReminderEvent(order.getId()))
            .forEach(orderReminderEvent -> orchestrationService.orchestrate(orderReminderEvent));

        List<Order> escalatedOrders = orders.stream()
            .map(order -> order.updateStatus(ORDER_REMINDED))
            .collect(Collectors.toList());

        repository.saveAll(escalatedOrders);
      }
    } catch (OptimisticLockException e) {
      delay();
      return false;
    }
    return true;
  }
}
