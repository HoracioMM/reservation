package reservation.test;

import java.util.Random;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import model.Badge;
import model.TravelAgency;
import model.Traveller;
import model.UserProfile;

public class AddNewUsersTest {

	private static Logger logger = Logger.getLogger(AddNewUsersTest.class);

	public static void main(String[] args) {

		Random random = new Random();
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {

			TravelAgency travelAgency = new TravelAgency();
			travelAgency.setName("agency" + random.nextInt(1000));

			Badge badge = new Badge();
			badge.setBadgeNr(Integer.toString(random.nextInt(1000000)));

			UserProfile userProfile = new UserProfile();
			userProfile.setUserType("Traveller");
			userProfile.setBadge(badge);

			Traveller traveller = new Traveller();
			traveller.setUsername("traveller" + random.nextInt(1000));
			traveller.setPassword(traveller.getUsername());

			traveller.setTravelAgency(travelAgency);
			traveller.setUserProfile(userProfile);

			session.saveOrUpdate(travelAgency);
			session.saveOrUpdate(badge);
			session.saveOrUpdate(userProfile);
			session.saveOrUpdate(traveller);

			transaction.commit();

		} catch (Exception e) {
			logger.error(e);
			transaction.rollback();
		}
		sessionFactory.getCurrentSession().close();
		sessionFactory.close();
	}

}
