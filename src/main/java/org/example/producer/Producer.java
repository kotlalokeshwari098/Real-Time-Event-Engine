package org.example.producer;

import org.example.domain.Event;
import org.example.domain.EventState;
import org.example.domain.EventType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Producer{
    public static void main(String[] args) throws InterruptedException {
        List<Event> events = Collections.synchronizedList(new ArrayList<>());
        int N=5;
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
                    events.add(e);
                }
            }
        };

       // Create N threads, each executing the same Runnable logic
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

        // Print total number of events and their IDs
        System.out.println(events.size());
        events.forEach(event ->{
            System.out.println(event.getId());
        });

    }
}
