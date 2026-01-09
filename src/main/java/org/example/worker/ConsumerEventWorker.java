package org.example.worker;

import org.example.domain.Event;
import org.example.domain.EventState;
import org.example.queue.EventsQueue;


public class ConsumerEventWorker {
    EventsQueue events;
    public ConsumerEventWorker(EventsQueue event){
       this.events=event;
    }
    public void consuming() throws InterruptedException {
        while(true){
            Event event=events.consume();
            event.setState(EventState.PROCESSING);
            try{
                Thread.sleep(1000);
                event.setState(EventState.PROCESSED);
            } catch (Exception e) {
                event.setState(EventState.FAILED);
            }
        }

    }

}
