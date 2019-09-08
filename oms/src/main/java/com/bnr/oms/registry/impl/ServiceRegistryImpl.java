package com.bnr.oms.registry.impl;

import com.bnr.oms.events.EventType;
import com.bnr.oms.notificator.Notificator;
import com.bnr.oms.registry.ServiceRegistry;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceRegistryImpl implements ServiceRegistry {

  @Autowired
  private List<Notificator> notificators;

  @Override
  public List<Notificator> find(final EventType eventType) {
    return notificators.stream()
        .filter(notificator -> notificator.supports(eventType))
        .collect(Collectors.toList());
  }
}
