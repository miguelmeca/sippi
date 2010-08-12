/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory object.
 *
 * @author Emmanuel
 */

/**
 * Basic Hibernate helper class, handles SessionFactory, Session and Transaction.
 * <p>
 * Uses a static initializer for the initial SessionFactory creation
 * and holds Session and Transactions in thread local variables. All
 * exceptions are wrapped in an unchecked InfrastructureException.
 *
 * @author christian@hibernate.org
 */
public class HibernateUtil {

	//private static Log log = LogFactory.getLog(HibernateUtil.class);

	private static Configuration configuration;
	private static SessionFactory sessionFactory;
	private static final ThreadLocal threadSession = new ThreadLocal();
	private static final ThreadLocal threadTransaction = new ThreadLocal();
	private static final ThreadLocal threadInterceptor = new ThreadLocal();

	// Create the initial SessionFactory from the default configuration files
	static {
		try {
			configuration = new Configuration();
			sessionFactory = configuration.configure("config/hibernate.cfg.xml").buildSessionFactory();
		} catch (Throwable ex) {

                        JOptionPane.showMessageDialog(new JFrame(),"No se puso iniciar:\n"+ex.getMessage(),"ERROR DE HIBERNATE",JOptionPane.ERROR_MESSAGE);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Returns the SessionFactory used for this static class.
	 *
	 * @return SessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Returns the original Hibernate configuration.
	 *
	 * @return Configuration
	 */
	public static Configuration getConfiguration() {
		return configuration;
	}

	/**
	 * Retrieves the current Session local to the thread.
	 * <p/>
	 * If no Session is open, opens a new Session for the running thread.
	 *
	 * @return Session
	 */
	public static Session getSession()
		throws Exception {
		Session s = (Session) threadSession.get();
		try {
			if (s == null) {
				System.out.println("Abriendo una nueva Sesion para este Hilo");
				if (getInterceptor() != null) {
					System.out.println("Ussando el interceptor: " + getInterceptor().getClass());
					s = getSessionFactory().openSession(getInterceptor());
				} else {
					s = getSessionFactory().openSession();
				}
				threadSession.set(s);
			}
		} catch (HibernateException ex) {
			throw new HibernateException(ex);
		}
		return s;
	}

	/**
	 * Closes the Session local to the thread.
	 */
	public static void closeSession()
		throws HibernateException {
		try {
			Session s = (Session) threadSession.get();
			threadSession.set(null);
			if (s != null && s.isOpen()) {
				System.out.println("Cerrando la sesion");
				s.close();
			}
		} catch (HibernateException ex) {
			throw new HibernateException(ex);
		}
	}

	/**
	 * Start a new database transaction.
	 */
	public static void beginTransaction()
		throws Exception {
		Transaction tx = (Transaction) threadTransaction.get();
		try {
			if (tx == null) {
				System.out.println("Comenzando una Transaccion");
				tx = getSession().beginTransaction();
				threadTransaction.set(tx);
			}
		} catch (HibernateException ex) {
			throw new HibernateException(ex);
		}
	}

	/**
	 * Commit the database transaction.
	 */
	public static void commitTransaction()
		throws HibernateException {
		Transaction tx = (Transaction) threadTransaction.get();
		try {
			if ( tx != null && !tx.wasCommitted()
							&& !tx.wasRolledBack() ) {
				System.out.println("Haciendo Commit en una Transaccion");
				tx.commit();
			}
			threadTransaction.set(null);
		} catch (HibernateException ex) {
			rollbackTransaction();
			throw new HibernateException(ex);
		}
	}

	/**
	 * Rollback the database transaction.
	 */
	public static void rollbackTransaction()
		throws HibernateException {
		Transaction tx = (Transaction) threadTransaction.get();
		try {
			threadTransaction.set(null);
			if ( tx != null && !tx.wasCommitted() && !tx.wasRolledBack() ) {
				System.out.println("Haciendo un RollBack en una transaccion");
				tx.rollback();
			}
		} catch (HibernateException ex) {
			throw new HibernateException(ex);
		} finally {
			closeSession();
		}
	}

	/**
	 * Disconnect and return Session from current Thread.
	 *
	 * @return Session the disconnected Session
	 */
	public static Session disconnectSession()
		throws HibernateException, Exception {

		Session session = getSession();
		try {
			threadSession.set(null);
			if (session.isConnected() && session.isOpen())
				session.disconnect();
		} catch (HibernateException ex) {
			throw new HibernateException(ex);
		}
		return session;
	}

	/**
	 * Register a Hibernate interceptor with the current thread.
	 * <p>
	 * Every Session opened is opened with this interceptor after
	 * registration. Has no effect if the current Session of the
	 * thread is already open, effective on next close()/getSession().
	 */
	public static void registerInterceptor(Interceptor interceptor) {
		threadInterceptor.set(interceptor);
	}

	private static Interceptor getInterceptor() {
		Interceptor interceptor =
			(Interceptor) threadInterceptor.get();
		return interceptor;
	}


        public static boolean isConected() throws Exception
         {
		Session session = getSession();
                try {
                    if (session.isConnected()&& session.isOpen())
                    {
                        return true;
                    }
                    return false;
		}catch (HibernateException ex)
                {
			throw new HibernateException(ex);
		}

        }

        public static String getCadenaConexion()
        {
            return configuration.getProperty("hibernate.connection.url");
        }
        public static String getUsuarioConexion()
        {
            return configuration.getProperty("hibernate.connection.username");
        }
        public static String getPasswordConexion()
        {
            return configuration.getProperty("hibernate.connection.password");
        }

}
