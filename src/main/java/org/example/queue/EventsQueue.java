package org.example.queue;

import org.example.domain.Event;
import org.example.domain.EventState;


import java.util.LinkedList;
import java.util.List;

//responsible for safely coordinating multiple producer and consumer
public class EventsQueue {
    private int maxCapacity;
    private BackpressurePolicy  backpressurePolicy;
    private  long timeoutMs;


    private final List<Event> events=new LinkedList<>();

    public EventsQueue(int i, BackpressurePolicy backpressurePolicy,long timeoutMs) {
        this.maxCapacity=i;
        this.backpressurePolicy=backpressurePolicy;
        this.timeoutMs=timeoutMs;
    }

    public synchronized void publish(Event event) throws InterruptedException {
        System.out.println(event.getState() +"️"+event.getId());

        while(events.size()>=maxCapacity){
            switch(backpressurePolicy){
                case BLOCK:
                    wait();
                    event.setState(EventState.WAITING);
                    break;
                case DROP:
                    event.setState(EventState.FAILED);
                    return;
                case TIMEOUT:
                    long startTime = System.currentTimeMillis();
                    wait(timeoutMs);

                    long waitedTime = System.currentTimeMillis() - startTime;

                    if (waitedTime >= timeoutMs && events.size() >= maxCapacity) {
                        // timeout happened, no space freed
                        return;
                    }
            }

        }
        events.add(event);
        event.setState(EventState.QUEUED);
        notifyAll();
    }

    //remove event from queue
    public synchronized Event consume() throws InterruptedException {
        while(events.isEmpty()){
            wait();
        }
        Event event=events.removeFirst();
        System.out.println(event.getState() +""+event.getId());
        notifyAll();
        return event;

    }



}
