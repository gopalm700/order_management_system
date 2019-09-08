package com.bnr.oms.notificator.impl;

import static com.bnr.oms.events.EventType.ORDER_NOTIFIED;
import static com.bnr.oms.events.EventType.ORDER_REMINDER;

import com.bnr.oms.events.EventType;
import com.bnr.oms.events.OrderEvent;
import com.bnr.oms.events.OrderNotify;
import com.bnr.oms.messaging.dto.QueueMessage;
import com.bnr.oms.notificator.Notificator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ResturantNotificator implements Notificator {


  @Value("${fanout.exchange}")
  private String fanoutExchange;

  private final RabbitTemplate rabbitTemplate;

  @Autowired
  public ResturantNotificator(RabbitTemplate rabbitTemplate) {
    super();
    this.rabbitTemplate = rabbitTemplate;
  }

  private static final Logger logger = LoggerFactory.getLogger(ResturantNotificator.class);

  @Override
  public void notify(final OrderEvent event) {
    if (event instanceof OrderNotify) {
      logger.info("Notifying Restaurant " + event.getOrderId());
      rabbitTemplate.setExchange(fanoutExchange);
      try {
        OrderNotify notifyEvent = (OrderNotify) event;

        QueueMessage message = QueueMessage.builder().deliveryTime(notifyEvent.getDeliveryTime())
            .orderId(notifyEvent.getOrderId())
            .quantity(notifyEvent.getQuantity())
            .callback("/api/callback")
            .action("CREATE_ORDER").build();

        rabbitTemplate.convertAndSend(new ObjectMapper().writeValueAsString(message));

      } catch (Exception e) {
        logger.error("Error occurred when notifying restaurant  " + event.orderId, e);
      }
    } else {
      QueueMessage message = QueueMessage.builder()
          .orderId(event.getOrderId())
          .action("REMINDER").build();
      logger.info("Reminding Restaurant " + event.getOrderId());
      try {
        rabbitTemplate.convertAndSend(new ObjectMapper().writeValueAsString(message));
      } catch (Exception e) {
        logger.error("Error occurred when notifying restaurant  " + event.orderId, e);
      }
    }
  }

  @Override
  public boolean supports(EventType eventType) {
    return eventType == ORDER_NOTIFIED || eventType == ORDER_REMINDER;
  }
}
