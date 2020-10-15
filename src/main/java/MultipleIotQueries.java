import com.microsoft.azure.sdk.iot.service.devicetwin.*;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;

import java.io.File;

import java.io.FileReader;
import java.io.IOException;

import java.util.HashMap;
import java.util.Properties;

public class MultipleIotQueries {
    public static void main(String[] args) throws IOException, IotHubException {
        //Reading iot hub configuration
        Properties properties = new Properties();
        properties.load(new FileReader(new File("src/main/resources/iothub.properties")));
        //Shared access keys-> from registryReadWrite policy
        String connectionString = properties.getProperty("connectionString");

        // Get the DeviceTwin  objects
        DeviceTwin twinClient = DeviceTwin.createFromConnectionString(connectionString);
        HashMap<String, String> queryMap = Utilities.getIotQueries();
        SqlQuery sqlQuery = null;
        for (String queryId : queryMap.keySet()) {
            String[] arguments = queryMap.get(queryId).split("\\|\\|");
            // Construct the query from HashMap values
            if (arguments[2].equals("null"))
                sqlQuery=SqlQuery.createSqlQuery(arguments[0], SqlQuery.FromType.DEVICES, arguments[1], null);
            else
                sqlQuery = SqlQuery.createSqlQuery(arguments[0], SqlQuery.FromType.DEVICES, arguments[1], arguments[2]);
            System.out.println("<-------Device ID's of " + queryId + "-------->");
            //Query the device twins in IoT Hub
            Query twinQuery = twinClient.queryTwin(sqlQuery.getQuery(), 100);
            while (twinClient.hasNextDeviceTwin(twinQuery)) {
                DeviceTwinDevice d = twinClient.getNextDeviceTwin(twinQuery);
                System.out.println(d.getDeviceId());
            }

        }
    }
}

