package com.bnr.oms.notificator;

import java.util.List;

public interface NotificatorFactory {

 List<Notificator> find(String eventType);

}
