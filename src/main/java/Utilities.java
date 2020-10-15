import java.util.HashMap;

public class Utilities {

    public static HashMap<Integer, String> wordMap = new HashMap<Integer, String>();
    public static HashMap<String, String> iotQueries = new HashMap<String, String>();

    public static HashMap<Integer, String> getWordMap() {
        wordMap.put(0, "Blob");
        wordMap.put(1, "Eventhub");
        wordMap.put(2, "HDInsight");
        wordMap.put(3, "EventTrigger");
        wordMap.put(4, "IOTHub");
        wordMap.put(5, "ActiveDirectory");
        return wordMap;
    }

    public static HashMap<String, String> getIotQueries() {
        iotQueries.put("ConnectedDevices", "*||connectionState='connected'||null");
        iotQueries.put("DisconnectedDevices", "*||connectionState='Disconnected'||null");
        iotQueries.put("EnabledDevices", "*||status='enabled'||null");
        iotQueries.put("DisablesDevices", "*||status='disabled'||null");
        return iotQueries;
    }
}
