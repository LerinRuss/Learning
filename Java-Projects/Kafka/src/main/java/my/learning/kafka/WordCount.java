package my.learning.kafka;

import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;

public class WordCount {
    public static void main(String[] args) {
        Properties props = new PropertiesBuilder()
            .put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-wordcount")
            .build();

        StreamsBuilder builder = new StreamsBuilder();

        builder.<String, String>stream("streams-plaintext-input")
            .flatMapValues(value -> Arrays.asList(
                value.toLowerCase(Locale.getDefault()).split("\\W+")))
            .groupBy((key, val) -> val)
            .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("counts-store"))
            .toStream().to("streams-wordcount-output", Produced.with(Serdes.String(), Serdes.Long()));

        Topology topology = builder.build();
        System.out.println(topology.describe());

        try {
            Utils.runStream(topology, props);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.exit(0);
    }
}
