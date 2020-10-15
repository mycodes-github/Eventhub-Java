import com.microsoft.azure.sdk.iot.service.devicetwin.*;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.Properties;


public class IotHubDeviceTwin {

    public static void main(String[] args) throws IOException, IotHubException {
        //Reading iot hub configuration
        Properties properties = new Properties();
        properties.load(new FileReader(new File("src/main/resources/iothub.properties")));
        //Shared access keys-> from registryReadWrite policy
        String connectionString = properties.getProperty("connectionString");


        // Get the DeviceTwin  objects
        DeviceTwin twinClient = DeviceTwin.createFromConnectionString(connectionString);

        // Query the device twins in IoT Hub
        // Construct the query
        SqlQuery sqlQuery1 = SqlQuery.createSqlQuery("*", SqlQuery.FromType.DEVICES, "connectionState='connected'", null);
        SqlQuery sqlQuery2 = SqlQuery.createSqlQuery("*", SqlQuery.FromType.DEVICES, "status='disabled'", null);

        // Run the query
        System.out.println("<-------Device ID's of connected Devices-------->");
        Query twinQuery = twinClient.queryTwin(sqlQuery1.getQuery(), 100);
        while (twinClient.hasNextDeviceTwin(twinQuery)) {
            DeviceTwinDevice d = twinClient.getNextDeviceTwin(twinQuery);
            System.out.println(d.getDeviceId());
        }

        System.out.println("<-------Device ID's of disabled Devices-------->");
        Query twinQuery2 = twinClient.queryTwin(sqlQuery2.getQuery(), 100);
        while (twinClient.hasNextDeviceTwin(twinQuery2)) {
            DeviceTwinDevice d = twinClient.getNextDeviceTwin(twinQuery2);
            System.out.println(d.getDeviceId());
        }

        // To construct and run multiple queries check MultipleIotQueries.java file

    }
}
