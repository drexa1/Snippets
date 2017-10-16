package me;

import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.operators.AbstractStreamOperator;
import org.apache.flink.streaming.api.operators.OneInputStreamOperator;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.runtime.streamrecord.StreamRecord;

import java.io.Serializable;

public class FlinkEventTimeProcessor {
    public static void main( String[] args ) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStream<Event> eventStream = env.addSource(new EventGenerator());
        DataStream<Event> timestampStream = eventStream.rebalance().assignTimestampsAndWatermarks(new TimestampExtractor(30000L));
        timestampStream.transform("OrderOperator", eventStream.getType(), new OrderOperator<>());

        env.execute("Run Stream smoother");
    }
}

class OrderOperator<T> extends AbstractStreamOperator<T> implements OneInputStreamOperator<T, T> {
    private static final long serialVersionUID = 607406801052018312L;

    @Override
    public void processElement(StreamRecord<T> streamRecord) throws Exception {
        // this method is called for each incoming stream record
        //System.out.println("StreamRecord " + streamRecord);
    }

    @Override
    public void processWatermark(Watermark watermark) throws Exception {
        // this method is called for each incoming watermark
        System.out.println("Watermark " + watermark);
    }
}

class TimestampExtractor implements AssignerWithPeriodicWatermarks<Event>, Serializable {
    private static final long serialVersionUID = 7526472295622776147L;
    long maxDelay = 0L;
    long currentWatermarkTimestamp = 0L;
    public TimestampExtractor(long maxDelay) {
        this.maxDelay = maxDelay;
    }
    @Override
    public long extractTimestamp(Event event, long previousElementTimestamp) {
        currentWatermarkTimestamp = Math.max(event.time, currentWatermarkTimestamp);
        return event.time;
    }
    @Override
    public Watermark getCurrentWatermark() {
        return new Watermark(currentWatermarkTimestamp - maxDelay);
    }
}
