package reservation.test;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import controller.service.HibernateService;
import model.TravelAgency;
import model.Traveller;

public class TravellerAgencyRelationTest {

	private static Logger logger = Logger.getLogger(TravellerAgencyRelationTest.class);

	public static void main(String[] args) {

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {

			TravelAgency agency = new TravelAgency();
			agency.setName("ta" + Double.toString(Math.random() * 1000 % 1000));
			HibernateService.getInstance().saveOrUpdate(agency);

			Traveller traveller = new Traveller();
			traveller.setUsername("t" + Double.toString(Math.random()));
			traveller.setPassword(Double.toString(Math.random()));
			traveller.setTravelAgency(agency);
			HibernateService.getInstance().saveOrUpdate(traveller);

			Criteria criteria = session.createCriteria(Traveller.class)
					.add(Restrictions.idEq(traveller.getTravellerId()));

			Traveller t = (Traveller) criteria.list().get(0);
			transaction.commit();
			logger.info(t.getTravelAgency().getName() + "====" + agency.getName());
		} catch (Exception e) {
			logger.error(e);
			transaction.rollback();
		}
		sessionFactory.close();

	}

}
