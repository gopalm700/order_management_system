package com.bnr.oms.workflow.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.bnr.oms.events.EventType;
import com.bnr.oms.events.OrderEscalateEvent;
import com.bnr.oms.notificator.Notificator;
import com.bnr.oms.registry.ServiceRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrchestrationServiceImplTest {

  @Mock
  private ServiceRegistry registry;

  @Mock
  private Notificator notificator;

  private OrchestrationServiceImpl orchestrationService;

  @Before
  public void setUp() {
    Mockito.reset(notificator, registry);
    orchestrationService = new OrchestrationServiceImpl(registry);
  }

  @Test
  public void shouldCallTwoNotifier() {
    when(registry.find(any(EventType.class))).thenReturn(Arrays.asList(notificator, notificator));
    orchestrationService.orchestrate(new OrderEscalateEvent("1244"));
    verify(registry, times(1)).find(any(EventType.class));
    verify(notificator, times(2)).notify(any(OrderEscalateEvent.class));
  }

  @Test
  public void shouldCallZeroNotificator() {
    when(registry.find(any(EventType.class))).thenReturn(new ArrayList<>());
    orchestrationService.orchestrate(new OrderEscalateEvent("1244"));
    verify(registry, times(1)).find(any(EventType.class));
    verifyNoMoreInteractions(notificator);
  }
}
