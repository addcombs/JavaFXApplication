package com.javaFXApplication.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**       Database Hibernate Session Factory
 * @author Adam Combs
 */
public class HibernateSessionFactory {

          /**
           *        Creates static Session Factory to call when querying database with hibernate.
           */
          public static SessionFactory getSessionFactory(){
                    SessionFactory sessionFactory;
                    try {
                              sessionFactory = new Configuration().configure().buildSessionFactory();
                    } catch (Exception ex) {
                              System.err.println("Failed to create session factory");
                              throw new ExceptionInInitializerError(ex);
                    }
                    return sessionFactory;
          }

}
