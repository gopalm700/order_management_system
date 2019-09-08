package com.bnr.oms.jobs;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.bnr.oms.events.OrderReminderEvent;
import com.bnr.oms.persistence.entity.Order;
import com.bnr.oms.persistence.entity.Order.OrderStatus;
import com.bnr.oms.persistence.repo.OrderRepository;
import com.bnr.oms.workflow.OrchestrationService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.persistence.OptimisticLockException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReminderJobTest {

  @Mock
  private OrderRepository repository;

  @Mock
  private OrchestrationService orchestrationService;

  private ReminderJob reminderJob;

  @Before
  public void setUp() {
    reset(repository, orchestrationService);
    reminderJob = new ReminderJob(repository, orchestrationService);
  }

  @Test
  public void shouldReturnWhenThereIsNoOrder() throws InterruptedException {
    when(repository
        .findAllByStatusAndNotifyTimeBeforeOrderByNotifyTimeAsc(any(OrderStatus.class),
            any(Date.class)))
        .thenReturn(new ArrayList<>());
    reminderJob.reminder();
    verify(repository, times(1))
        .findAllByStatusAndNotifyTimeBeforeOrderByNotifyTimeAsc(any(OrderStatus.class),
            any(Date.class));
    verifyNoMoreInteractions(orchestrationService);
    verifyNoMoreInteractions(repository);
  }

  @Test
  public void shouldNotifyIfOrderExists() throws InterruptedException {
    when(repository
        .findAllByStatusAndNotifyTimeBeforeOrderByNotifyTimeAsc(any(OrderStatus.class),
            any(Date.class)))
        .thenReturn(Arrays.asList(Order.builder().id("122").build()));
    doNothing().when(orchestrationService).orchestrate(any(OrderReminderEvent.class));
    when(repository.saveAll(any(Iterable.class))).thenReturn(new ArrayList<>());
    reminderJob.reminder();
    verify(repository, times(1))
        .findAllByStatusAndNotifyTimeBeforeOrderByNotifyTimeAsc(any(OrderStatus.class),
            any(Date.class));
    verify(orchestrationService, times(1)).orchestrate(any(OrderReminderEvent.class));
    verify(repository, times(1)).saveAll(any(Iterable.class));
  }

  @Test
  public void shouldRetryWhenFails() throws InterruptedException {
    when(repository
        .findAllByStatusAndNotifyTimeBeforeOrderByNotifyTimeAsc(any(OrderStatus.class),
            any(Date.class)))
        .thenReturn(Arrays.asList(Order.builder().id("122").build()));
    doNothing().when(orchestrationService).orchestrate(any(OrderReminderEvent.class));
    when(repository.saveAll(any(Iterable.class))).thenThrow(OptimisticLockException.class);
    reminderJob.reminder();
    verify(repository, times(3))
        .findAllByStatusAndNotifyTimeBeforeOrderByNotifyTimeAsc(any(OrderStatus.class),
            any(Date.class));
    verify(orchestrationService, times(3)).orchestrate(any(OrderReminderEvent.class));
    verify(repository, times(3)).saveAll(any(Iterable.class));
  }
}
