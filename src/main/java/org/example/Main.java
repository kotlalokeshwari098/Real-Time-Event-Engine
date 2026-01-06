package org.example;

import org.example.producer.Producer;
import org.example.queue.EventsQueue;
import org.example.worker.ConsumerEventWorker;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {

        EventsQueue sharedQueue=new EventsQueue();

        Producer producer=new Producer(sharedQueue);
        producer.produce();
        ConsumerEventWorker worker=new ConsumerEventWorker(sharedQueue);
        Thread workerThread=new Thread(()->{
            try{
                worker.consuming();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        });
        workerThread.start();

    }
}

