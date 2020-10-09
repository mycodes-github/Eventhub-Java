
import com.azure.messaging.eventhubs.*;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;

public class EventhubProducer {
    public static void main(String[] args) throws InterruptedException, IOException {

        Properties properties = new Properties();
        properties.load(new FileReader(new File("src/main/resources/eventhub.properties")));

        final String connectionString = properties.getProperty("connectionString");
        final String eventHubName = properties.getProperty("eventHubName");
        HashMap<Integer, String> wordMap = Utilities.getWordMap();
        Random random = new Random();

        EventHubProducerClient producer = new EventHubClientBuilder()
                .connectionString(connectionString, eventHubName)
                .buildProducerClient();

        EventDataBatch batch = producer.createBatch();

        for (int i = 0; i < 10; i++) {
            batch.tryAdd(new EventData(wordMap.get(random.nextInt(5))));
            batch.tryAdd(new EventData(wordMap.get(random.nextInt(5))));
            batch.tryAdd(new EventData(wordMap.get(random.nextInt(5))));
            producer.send(batch);
            Thread.sleep(5000);
        }
        producer.close();
    }
}
