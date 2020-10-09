import java.util.HashMap;

public class Utilities {

    public static HashMap<Integer, String> wordMap = new HashMap<Integer, String>();

    public static HashMap<Integer, String> getWordMap() {
        wordMap.put(0, "Blob");
        wordMap.put(1, "Eventhub");
        wordMap.put(2, "HDInsight");
        wordMap.put(3, "EventTrigger");
        wordMap.put(4, "IOTHub");
        wordMap.put(5, "ActiveDirectory");
        return wordMap;
    }
}
