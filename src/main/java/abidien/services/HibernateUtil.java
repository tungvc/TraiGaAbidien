package abidien.services;

import abidien.common.Config;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

import java.util.Properties;

/**
 * Created by ABIDIEN on 03/07/2016.
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml)
            // config file.
            Properties prop= new Properties();
            prop.setProperty("hibernate.connection.url", Config.dbUrl);
            prop.setProperty("hibernate.connection.username", Config.dbUsername);
            prop.setProperty("hibernate.connection.password", Config.dbPassowrd);

            sessionFactory = new Configuration().configure().addProperties(prop).buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception.
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
