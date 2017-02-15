package zan.delivery_services.db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ConnectionFactory {
	
	private static final SessionFactory sessionFactory = getSessionFactory();
	
	private static SessionFactory getSessionFactory(){
		Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        
        return sessionFactory;
    }
	
	public static Session getSession() throws HibernateException{
        return sessionFactory.openSession();
    }

}
