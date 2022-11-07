package my.learning.kafka;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;

public class PropertiesBuilder {
    private Map<Object, Object> builderProps = new HashMap<>() {{
        put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    }};

    public PropertiesBuilder put(Object key, Object value) {
        builderProps.put(key, value);

        return this;
    }


    public Properties build() {
        Properties properties = new Properties();
        properties.putAll(builderProps);

        return properties;
    }
}
