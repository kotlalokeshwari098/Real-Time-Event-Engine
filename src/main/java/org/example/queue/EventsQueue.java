package org.example.queue;

import org.example.domain.Event;
import org.example.domain.EventState;


import java.util.LinkedList;
import java.util.List;

//responsible for safely coordinating multiple producer and consumer
public class EventsQueue {
    private final int maxCapacity=100;
    private final List<Event> events=new LinkedList<>();

    public synchronized void publish(Event event) throws InterruptedException {
        System.out.println(event.getState() +""+event.getId());
        while(events.size() >= maxCapacity){
                wait();
            System.out.println(event.getState() +""+event.getId());
        }

        System.out.println(event.getState() +" "+event.getId());
        events.add(event);
        event.setState(EventState.QUEUED);
        notifyAll();
        System.out.println(event.getState() +""+event.getId());
        System.out.println(events.size());


    }

    //remove event from queue
    public synchronized Event consume() throws InterruptedException {

        while(events.isEmpty()){
            wait();
        }
        Event event=events.removeFirst();
        event.setState(EventState.PROCESSED);
        System.out.println(event.getState() +""+event.getId());
        notifyAll();
        return event;

    }



}
