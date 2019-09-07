package com.bnr.oms.notificator.impl;

import com.bnr.oms.notificator.Notificator;
import com.bnr.oms.notificator.NotificatorFactory;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificatorFactoryImpl implements NotificatorFactory {

  @Autowired
  private List<Notificator> notificators;

  @Override
  public List<Notificator> find(String eventType) {
    return notificators.stream().filter(notificator -> notificator.supports(eventType))
        .collect(Collectors.toList());
  }
}
