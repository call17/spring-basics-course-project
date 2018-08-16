package com.yet.spring.core;

import com.yet.spring.core.beans.EventType;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yet.spring.core.beans.Client;
import com.yet.spring.core.beans.Event;
import com.yet.spring.core.loggers.EventLogger;

import java.util.Map;

public class App {

    private Client client;
    private EventLogger eventLogger;
    private Map<EventType, EventLogger> eventTypeEventLoggerMap;
    
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        App app = (App) ctx.getBean("app");
        
        Event event = ctx.getBean(Event.class);
        app.logEvent(EventType.INFO,event);

        Event event2 = ctx.getBean(Event.class);
        app.logEvent(EventType.ERROR, event2);
        
        ctx.close();
    }
    
    public App(Client client, EventLogger eventLogger, Map<EventType, EventLogger> eventTypeEventLoggerMap) {
        super();
        this.client = client;
        this.eventLogger = eventLogger;
        this.eventTypeEventLoggerMap = eventTypeEventLoggerMap;
    }

    private void logEvent(EventType eventType, Event event) {
        EventLogger eventLogger = eventTypeEventLoggerMap.get(eventType);
        if (eventLogger == null){
            eventLogger = this.eventLogger;
        }
        event.setMsg(this.client.getFullName());
        eventLogger.logEvent(event);
    }

}
