package abidien.common;

import com.netflix.config.DynamicPropertyFactory;

/**
 * Created by ABIDIEN on 31/07/2016.
 */
public class Config {
    static DynamicPropertyFactory factory = DynamicPropertyFactory.getInstance();
    public static String host = factory.getStringProperty("host", "http://localhost:8080/").getValue();
    public static int port = factory.getIntProperty("host", 8080).getValue();
}
