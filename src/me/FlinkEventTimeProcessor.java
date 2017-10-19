package me;

import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.checkpoint.ListCheckpointed;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.source.ParallelSourceFunction;
import org.apache.flink.streaming.api.operators.AbstractStreamOperator;
import org.apache.flink.streaming.api.operators.OneInputStreamOperator;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.runtime.streamrecord.StreamRecord;

import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class FlinkEventTimeProcessor {
    public static void main( String[] args ) throws Exception {

        args = new String[] { "-state.checkpoints.dir", "file:///tmp/events/checkpoints" };

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        ParameterTool parameters = ParameterTool.fromArgs(args);
        env.getConfig().setGlobalJobParameters(parameters);

        // Fault tolerance config
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.enableCheckpointing(1000);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(10, Time.of(10, TimeUnit.SECONDS)));

        DataStream<Event> eventStream = env.addSource(new EventGenerator());
        DataStream<Event> timestampStream = eventStream.assignTimestampsAndWatermarks(new TimestampExtractor());
        KeyedStream keyedStream = timestampStream.keyBy("someData");
        keyedStream.transform("OrderOperator", keyedStream.getType(), new OrderOperator<>());
        keyedStream.print().name("print-sink");

        env.execute("Run Stream smoother");
    }
}

class OrderOperator<T> extends AbstractStreamOperator<T> implements OneInputStreamOperator<T, T> {
    private PriorityQueue<StreamRecord> queue;
    private Watermark currentWatermark;

    public OrderOperator() {
        if (queue == null) {
            queue = new PriorityQueue(1000);
        }
        currentWatermark = new Watermark(0L);
    }

    @Override
    public void processElement(StreamRecord<T> streamRecord) throws Exception {
        // this method is called for each incoming stream record
        if (streamRecord.getTimestamp() > currentWatermark.getTimestamp()) {
            queue.add(streamRecord);
        }
    }

    @Override
    public void processWatermark(Watermark watermark) throws Exception {
        // this method is called for each incoming watermark
        this.currentWatermark = watermark;
        StreamRecord head = queue.peek();
        while (head != null && (head.getTimestamp() <= watermark.getTimestamp())) {
            output.collect(head);
            queue.remove(head);
            head = queue.peek();
        }
    }
}

/**
 * This generator generates watermarks assuming that elements arrive out of order,
 * but only to a certain degree. The latest elements for a certain timestamp t will arrive
 * at most n milliseconds after the earliest elements for timestamp t.
 */
class TimestampExtractor implements AssignerWithPeriodicWatermarks<Event> {
    private final long maxOutOfOrderness = 30_000L; //first can range from 00_000 to 29_999
    private long currentMaxTimestamp;
    @Override
    public long extractTimestamp(Event event, long previousElementTimestamp) {
        currentMaxTimestamp = Math.max(event.time, currentMaxTimestamp);
        return event.time;
    }
    @Override
    public Watermark getCurrentWatermark() {
        // return the watermark as current highest timestamp minus the out-of-orderness bound
        return new Watermark(currentMaxTimestamp - maxOutOfOrderness);
    }
}

class EventGenerator implements ParallelSourceFunction<Event>, ListCheckpointed<Long> {
    private static final long serialVersionUID = 9159457797902106334L;
    private boolean running = true;
    public static final long OutOfOrderness = 15000L;
    public long counter = OutOfOrderness;

    public void run(SourceContext<Event> sourceContext) throws Exception {
        StringBuffer buf = new StringBuffer();
        while (running) {
            Event evt = new Event();
            long random = ThreadLocalRandom.current().nextLong(-OutOfOrderness, OutOfOrderness);
            // System.out.println("EventGenerator random: " + random);
            evt.time = counter + random;
            buf.append("original index:").append(counter).append(" time: ").append(evt.time);
            evt.someData = buf.toString();
            buf.setLength(0);
            synchronized (sourceContext.getCheckpointLock()) {
                sourceContext.collect(evt);
                counter++;
            }
            if(counter % 1_000L == 0) {
                Thread.sleep(10);
            }
        }
    }

    public void cancel() {
        running = false;
    }

    @Override
    public List<Long> snapshotState(long checkpointId, long timestamp) throws Exception {
        return Collections.singletonList(counter);
    }

    @Override
    public void restoreState(List<Long> state) throws Exception {
        counter = state.get(0);
    }
}
