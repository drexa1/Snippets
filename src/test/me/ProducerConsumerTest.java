package me;

import org.junit.Test;
import org.testng.collections.Sets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by dr186049 on 6/2/2017.
 */
public class ProducerConsumerTest {

    private final int MAX_CAPACITY = 50;
    private final BlockingQueue<String> queue = new ArrayBlockingQueue<>(MAX_CAPACITY);

    final Producer<String> p1 = new Producer<String>("p1", queue);
    final Producer<String> p2 = new Producer<String>("p2", queue);
    final Producer<String> p3 = new Producer<String>("p3", queue);
    final Producer<String> p4 = new Producer<String>("p4", queue);

    final Consumer<String> c1 = new Consumer<String>("c1", queue);
    final Consumer<String> c2 = new Consumer<String>("c2", queue);
    final Consumer<String> c3 = new Consumer<String>("c3", queue);
    final Consumer<String> c4 = new Consumer<String>("c4", queue);

    private ProducerConsumer pc;


    @Test
    public void manyProducersTest() throws InterruptedException {
        Set<Producer> producers = Sets.newHashSet(Arrays.asList(p1, p2, p3, p4));
        Set<Consumer> consumers = Sets.newHashSet(Arrays.asList(c1, c2));
        pc = new ProducerConsumer(producers, consumers);
        Thread.sleep(Long.MAX_VALUE);
    } // mostly full queue

    @Test
    public void manyConsumersTest() throws InterruptedException {
        Set<Producer> producers = Sets.newHashSet(Arrays.asList(p1, p2));
        Set<Consumer> consumers = Sets.newHashSet(Arrays.asList(c1, c2, c3, c4));
        pc = new ProducerConsumer(producers, consumers);
        Thread.sleep(Long.MAX_VALUE);
    } // mostly empty queue


}
