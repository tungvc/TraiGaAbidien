package abidien.common;

import com.netflix.config.DynamicPropertyFactory;

import java.io.File;

/**
 * Created by ABIDIEN on 31/07/2016.
 */
public class Config {
    static DynamicPropertyFactory factory = DynamicPropertyFactory.getInstance();
    public static String host = factory.getStringProperty("host", "http://localhost:8080/").getValue();
    public static int port = factory.getIntProperty("port", 8080).getValue();
    public static String adsenseDB = factory.getStringProperty("adsense.db", System.getProperty("user.home") + "/.store/adsense_management_sample").getValue();

    public static String dbUrl = factory.getStringProperty("db.url", "jdbc:mysql://localhost:3306/chuong_ga").getValue();
    public static String dbUsername = factory.getStringProperty("db.username", "root").getValue();
    public static String dbPassowrd = factory.getStringProperty("db.password", "").getValue();

    //Auto post
    public static int apPort = factory.getIntProperty("ap.port", 8081).getValue();
    public static String uploadDir = factory.getStringProperty("upload.dir", "D:/upload_dir").getValue();

    //Auto post
    public static int renamePort = factory.getIntProperty("ap.port", 8082).getValue();
}

