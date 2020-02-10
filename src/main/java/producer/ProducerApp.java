package producer;

public class ProducerApp {

    public static final String TOPIC = "test1";

    public static void main(String[] args) {
        boolean isAsync = false;
        ThreadProducer producerThread = new ThreadProducer(TOPIC, isAsync);
        // start the producer
        producerThread.start();

    }
}

