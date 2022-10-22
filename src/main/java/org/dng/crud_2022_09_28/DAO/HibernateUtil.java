package org.dng.crud_2022_09_28.DAO;


import org.dng.crud_2022_09_28.Model.VinylRecord;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;


public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Properties properties= new Properties();
            properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/vinyla_db");
            properties.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            properties.setProperty("hibernate.connection.username", "root");
            properties.setProperty("hibernate.connection.password", "dingo1975");
            properties.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
            properties.setProperty("show_sql", "true");

            sessionFactory = new Configuration()
                    .addPackage("org.dng.crud_2022_09_28.Model")//package where entity is placed
                    .addProperties(properties)
                    .addAnnotatedClass(VinylRecord.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static Session getSession()
            throws HibernateException {
        return sessionFactory.openSession();
    }
}