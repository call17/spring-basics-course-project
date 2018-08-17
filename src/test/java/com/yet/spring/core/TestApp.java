package com.yet.spring.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

import com.yet.spring.core.beans.EventType;
import org.junit.Test;

import com.yet.spring.core.beans.Client;
import com.yet.spring.core.beans.Event;
import com.yet.spring.core.loggers.EventLogger;

public class TestApp {
	
    private static final String MSG = "Hello";
//    private static final Map<EventType, EventLogger> stub = new HashMap(EventType.ERROR,new DummyLogger());

    @Test
    public void test() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Client client = new Client("25", "Bob");
        DummyLogger dummyLogger = new DummyLogger();

        App app = new App(client, dummyLogger, new HashMap<>());

        Event event = new Event(new Date(), DateFormat.getDateTimeInstance());

        invokeLogEvent(app, event, EventType.INFO);
        assertTrue(dummyLogger.getEvent().getMsg().contains(client.getFullName()));

        invokeLogEvent(app, event, EventType.INFO);
        assertFalse(dummyLogger.getEvent().getMsg().contains(client.getFullName()));
    }

    private void invokeLogEvent(App app, Event event, EventType eventType) throws NoSuchMethodException,
        IllegalAccessException, InvocationTargetException {
        Method method = app.getClass().getDeclaredMethod("logEvent", EventType.class, Event.class);
        method.setAccessible(true);
        method.invoke(app, eventType, event);
    }
    
    private class DummyLogger implements EventLogger {
        
        private Event event;

        @Override
        public void logEvent(Event event) {
            this.event = event;
        }

        public Event getEvent() {
            return event;
        }
        
    }

}
