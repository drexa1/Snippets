package main.me;

import me.ProducerConsumer;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by dr186049 on 6/2/2017.
 */
public class ProducerConsumerTest {

    private final int MAX_CAPACITY = 50;
    private final BlockingQueue<String> queue = new ArrayBlockingQueue<>(MAX_CAPACITY);

    final Producer<String> p1 = new Producer<>("p1", queue);
    final Producer<String> p2 = new Producer<>("p2", queue);
    final Producer<String> p3 = new Producer<>("p3", queue);
    final Producer<String> p4 = new Producer<>("p4", queue);

    final Consumer<String> c1 = new Consumer<>("c1", queue);
    final Consumer<String> c2 = new Consumer<>("c2", queue);
    final Consumer<String> c3 = new Consumer<>("c3", queue);
    final Consumer<String> c4 = new Consumer<>("c4", queue);

    private ProducerConsumer pc;

    @Test
    public void manyProducersTest() throws InterruptedException {
        List<Producer> producers = Arrays.asList(p1, p2, p3, p4);
        List<Consumer> consumers = Arrays.asList(c1, c2);
        pc = new ProducerConsumer(producers, consumers);
        Thread.sleep(Long.MAX_VALUE);
    } // mostly full queue

    @Test
    public void manyConsumersTest() throws InterruptedException {
        List<Producer> producers = Arrays.asList(p1, p2);
        List<Consumer> consumers = Arrays.asList(c1, c2, c3, c4);
        pc = new ProducerConsumer(producers, consumers);
        Thread.sleep(Long.MAX_VALUE);
    } // mostly empty queue

}
