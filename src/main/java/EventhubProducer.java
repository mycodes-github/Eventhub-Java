
import com.azure.messaging.eventhubs.*;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class EventhubProducer {
    private static final Logger log = Logger.getLogger(EventhubProducer.class);

    public static void main(String[] args) throws InterruptedException, IOException {

        log.addAppender(new ConsoleAppender(new PatternLayout("[%p] %d - %m%n")));
        log.setLevel(Level.ALL);

        log.info("Reading event hub properties file");
        Properties properties = new Properties();
        properties.load(new FileReader(new File("src/main/resources/eventhub.properties")));
        log.info("Event hub properties file loaded successfully");


        final String connectionString = properties.getProperty("connectionString");
        final String eventHubName = properties.getProperty("eventHubName");
        HashMap<Integer, String> wordMap = Utilities.getWordMap();
        Random random = new Random();

        log.info("Building eventhub client");
        EventHubProducerClient producer = new EventHubClientBuilder()
                .connectionString(connectionString, eventHubName)
                .buildProducerClient();
        log.info("creating batch to publish the data");
        EventDataBatch batch = producer.createBatch();

        log.info("Batch got created, starting to publish data");

        for (int i = 0; i < 10; i++) {
            log.debug("Loading data to eventhub batchNo.-->" + i);
            batch.tryAdd(new EventData(wordMap.get(random.nextInt(5))));
            batch.tryAdd(new EventData(wordMap.get(random.nextInt(5))));
            batch.tryAdd(new EventData(wordMap.get(random.nextInt(5))));
            producer.send(batch);
            log.debug("Successfully published the data to eventhub. batchNo.-->" + i);
            Thread.sleep(5000);
        }

        producer.close();
        log.info("All batch data published successfully");
    }
}
