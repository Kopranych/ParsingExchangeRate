package parsExchangeRate.dbService;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;

import parsExchangeRate.dbService.dao.ExchangeRateDAO;
import parsExchangeRate.dbService.dataSet.ExchangeRateDataSet;

public class DBService {
	private static final String hibernate_show_sql = "false";
    private static final String hibernate_hbm2ddl_auto = "validate";

    private final SessionFactory sessionFactory;

    public DBService() {
        Configuration configuration = getPostgresqlConnection();
        sessionFactory = createSessionFactory(configuration);
    }

    @SuppressWarnings("unused")
    public static Configuration getPostgresqlConnection() {
    	 Configuration configuration = new Configuration();
         configuration.addAnnotatedClass(ExchangeRateDataSet.class);

         configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
         configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
         configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/ExchangeRate");
         configuration.setProperty("hibernate.connection.username", "postgres");
         configuration.setProperty("hibernate.connection.password", "Kopranych25");
         configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
         configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
         return configuration;
    	
    }
    
    public ExchangeRateDataSet getExchangeRateById(long id) throws DBException {
    	Session session = null;
    	ExchangeRateDataSet dataSet;
        try {
        	session = sessionFactory.openSession();
            ExchangeRateDAO dao = new ExchangeRateDAO(session);
            dataSet = dao.get(id);
         
        } catch (HibernateException e) {
            throw new DBException(e);
        }finally {
        	if(session != null&& session.isOpen()) {
        		session.close();
        	}
        }
        return dataSet;
    }
    
    public List<ExchangeRateDataSet> getExchangeRateList() throws DBException {
    	Session session = null;
    	List<ExchangeRateDataSet> dataSetList;
    	try {
            session = sessionFactory.openSession();
            ExchangeRateDAO dao = new ExchangeRateDAO(session);
            dataSetList = (List<ExchangeRateDataSet>) dao.getList();
            
            
        } catch (HibernateException e) {
            throw new DBException(e);
        }finally{
        	session.close();
        }
    	return dataSetList;
    }

    public long addExchangeRate(Date systemDate, String time, String date, double usd, double eur) throws DBException {
    	Session session = null;
    	long id;
    	try {
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            ExchangeRateDAO dao = new ExchangeRateDAO(session);
            id = dao.inserExchangeRate(systemDate, time, date, usd, eur);
            transaction.commit();
            
        } catch (HibernateException e) {
            throw new DBException(e);
        }finally {
        	session.close();
        }
    	return id;
    }

    public void printConnectInfo() {
        try {
            SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sessionFactory;
            Connection connection = sessionFactoryImpl.getConnectionProvider().getConnection();
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
