package reservation.test;

import java.util.Random;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import model.Admin;

public class AddAdminTest {

	private static Logger logger = Logger.getLogger(AddAdminTest.class);

	public static void main(String[] args) {

		Random random = new Random();
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {

			Admin admin = new Admin();
			admin.setUsername("Horacio" + random.nextInt(1000));
			admin.setPassword(admin.getUsername());

			session.saveOrUpdate(admin);

			transaction.commit();

		} catch (Exception e) {
			logger.error(e);
			transaction.rollback();
		}
		sessionFactory.getCurrentSession().close();
		sessionFactory.close();
	}

}
