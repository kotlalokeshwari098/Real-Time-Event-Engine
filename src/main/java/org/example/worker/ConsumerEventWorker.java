package org.example.worker;

import org.example.queue.EventsQueue;


public class ConsumerEventWorker {
    EventsQueue events;
    public ConsumerEventWorker(EventsQueue event){
       this.events=event;
    }
    public void consuming() throws InterruptedException {
        while(true){
            events.consume();
            Thread.sleep(500);
        }

    }

}
