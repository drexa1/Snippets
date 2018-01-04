package me;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by dr186049 on 6/2/2017.
 */
public class ProducerConsumer {

    private final int MAX_CAPACITY = 50;
    private final BlockingQueue<String> queue = new ArrayBlockingQueue<>(MAX_CAPACITY);

    private List<Producer> producers;
    private List<Consumer> consumers;

    public ProducerConsumer(List<Producer> producers, List<Consumer> consumers) {
        for(Producer producer: producers) {
            startProducer(producer);
        }
        for(Consumer consumer: consumers) {
            startConsumer(consumer);
        }
    }

    private void startProducer(Producer producer) {
            new Thread(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("Created new Producer " + threadName);
                final AtomicInteger id = new AtomicInteger();
                final Supplier<String> supplier = () -> "Item" + id.incrementAndGet();
                producer.produce(supplier);
            }).start();
    }

    private void startConsumer(Consumer consumer) {
        new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Created new Consumer " + threadName);
            consumer.consume();
        }).start();
    }
}

class Producer<T> {
    private String name;
    private BlockingQueue<T> queue;
    public Producer(String name, BlockingQueue<T> queue) {
        this.name = name;
        this.queue = queue;
    }
    public void produce(Supplier<T> supplier) {
        while(true) {
            try {
                final T item = supplier.get();
                queue.put(item);
                System.out.println(this.name + " produced item: " + item + " (queue size: " + queue.size() + ")");
                int delay = 1000 + (new Random()).nextInt((1000) + 1);
                MILLISECONDS.sleep(delay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Consumer<T>{
    private String name;
    private BlockingQueue<T> queue;
    public Consumer(String name, BlockingQueue<T> queue) {
        this.name = name;
        this.queue = queue;
    }
    public void consume() {
        while(true) {
            try {
                T item = queue.take();
                System.out.println(this.name + " consumed item: " + item + " (queue size: " + queue.size() + ")");
                int delay = 1000 + (new Random()).nextInt((1000) + 1);
                MILLISECONDS.sleep(delay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}