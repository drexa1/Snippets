package me;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by dr186049 on 6/2/2017.
 */

class Producer<T> {
    private String name;
    private BlockingQueue<T> queue;
    public Producer(String name, BlockingQueue<T> queue) {
        this.name = name;
        this.queue = queue;
    }
    public void produce(Supplier<T> supplier) {
        try {
            final T item = supplier.get();
            queue.put(item);
            System.out.println(this.name + " produced item: " + item);
            MILLISECONDS.sleep(900);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
        try {
            T item = queue.take();
            System.out.println(this.name + " consumed item: " + item);
            MILLISECONDS.sleep(1250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

public class ProducerConsumer {

    private static final int MAX_CAPACITY = 500;
    private static final BlockingQueue<String> queue = new ArrayBlockingQueue<>(MAX_CAPACITY);

    public static void main(String args[]) {

        final Producer<String> producer1 = new Producer<String>("p1", queue);
        final Producer<String> producer2 = new Producer<String>("p2", queue);
        final Producer<String> producer3 = new Producer<String>("p3", queue);
        startProducer(producer1);
        startProducer(producer2);
        startProducer(producer3);

        final Consumer<String> consumer1 = new Consumer<String>("c1", queue);
        final Consumer<String> consumer2 = new Consumer<String>("c2", queue);
        startConsumer(consumer1);
        startConsumer(consumer2);
    }

    private static void startProducer(Producer producer) {
        final AtomicInteger id = new AtomicInteger();
        final Supplier<String> supplier = () -> "Item" + id.incrementAndGet();
        while (queue.size() < MAX_CAPACITY) {
            new Thread(() -> {
                producer.produce(supplier);
            }).start();
        }
    }

    private static void startConsumer(Consumer consumer) {
        while (!queue.isEmpty()) {
            new Thread(() -> {
                //TODO
                consumer.consume();
            }).start();
        }
    }
}
