import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConsumerApp {
    private static int i=0;
    public static void main(String[] args) {
        ThreadConsumer threadConsumer = new ThreadConsumer("test1");
        threadConsumer.start();
    }

    public static void StreamConsumer() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.43.129:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-groupe-1");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        System.out.println("============ Liste des TOPIC ============");
        kafkaConsumer.listTopics().forEach((s, partitionInfos) -> {
            System.out.println("Topic => " + s);
        });
        kafkaConsumer.subscribe(Collections.singletonList("test"));

        Executors.newScheduledThreadPool(5).scheduleAtFixedRate(() -> {
            System.out.println("============ 0" + " milliseconde");
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(1000);
            consumerRecords.forEach(cr -> {
                System.out.println("Key => " + cr.key() + "\n");
                System.out.println("Value => " + cr.value() + "\n");
                System.out.println("offset => " + cr.offset() + "\n");
            });
        }, 1000, 1000, TimeUnit.MILLISECONDS);

    }
}

