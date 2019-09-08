package com.bnr.oms.registry.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.bnr.oms.events.EventType;
import com.bnr.oms.notificator.Notificator;
import com.bnr.oms.notificator.impl.OrderCloseNotificator;
import com.bnr.oms.notificator.impl.OrderCreatedNotificator;
import com.bnr.oms.notificator.impl.ResturantNotificator;
import com.bnr.oms.registry.ServiceRegistry;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ServiceRegistryImplTest {

  private List<Notificator> notificators = Arrays.asList(
      new OrderCloseNotificator(null),
      new OrderCreatedNotificator(null),
      new ResturantNotificator(null)
  );

  private ServiceRegistry registry = new ServiceRegistryImpl(notificators);

  @Test
  public void shouldReturnRestaurantNotificator() {
    List<Notificator> notificator = registry.find(EventType.ORDER_REMINDER);
    assertThat(notificator.size()).isEqualTo(1);
    assertThat(notificator.get(0)).isInstanceOf(ResturantNotificator.class);
  }

  @Test
  public void shouldReturnOrderCreatedNotificator() {
    List<Notificator> notificator = registry.find(EventType.ORDER_CREATED);
    assertThat(notificator.size()).isEqualTo(1);
    assertThat(notificator.get(0)).isInstanceOf(OrderCreatedNotificator.class);
  }

  @Test
  public void shouldReturnOrderClosedNotificator() {
    List<Notificator> notificator = registry.find(EventType.ORDER_CLOSE);
    assertThat(notificator.size()).isEqualTo(1);
    assertThat(notificator.get(0)).isInstanceOf(OrderCloseNotificator.class);
  }

}
