package org.example.producer;

import org.example.domain.Event;
import org.example.domain.EventState;
import org.example.domain.EventType;
import org.example.queue.EventsQueue;

import java.time.LocalDateTime;
import java.util.*;

//multiple concurrent producers generating events safely
public class Producer{
    EventsQueue sharedQueue;
    //storing all events from all producers
//        List<Event> events = Collections.synchronizedList(new ArrayList<>());
    public Producer(EventsQueue sharedQueue){
        this.sharedQueue=sharedQueue;
    }

    public void produce() throws InterruptedException {
        int N=5; //number of producers
        // List<Event> events=new ArrayList<>();
        // Runnable logic: produces 5 events when executed by a thread
        Runnable logic= new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    Event e=new Event.Builder()
                            .uuid(UUID.randomUUID())
                            .localdatetime(LocalDateTime.now())
                            .eventstate(EventState.CREATED)
                            .eventtype(EventType.ACTION)
                            .build();
                    // Add event to a thread-safe list to avoid race conditions
                    try {
                        sharedQueue.publish(e);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    //critical section
                }
            }
        };

       // Create N threads, each executing the same Runnable logic
        // stores thread objects
       List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            Thread t=new Thread(logic);
            t.start();
            threads.add(t);
        // System.out.println(t.threadId()+" "+t.currentThread().getState());
        }

        // Wait for all threads to finish before main thread continues
        for (Thread t : threads) {
        // System.out.println(t);
            t.join();
        }

    }
}
