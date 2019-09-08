package com.bnr.oms.registry;

import com.bnr.oms.events.EventType;
import com.bnr.oms.notificator.Notificator;
import java.util.List;

public interface ServiceRegistry {

 List<Notificator> find(EventType eventType);

}
