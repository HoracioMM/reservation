package reservation.test;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import controller.service.HibernateService;
import model.Traveller;

public class HibernateServiceTest {

	private static Logger logger = Logger.getLogger(HibernateServiceTest.class);

	public static void main(String[] args) {

		List<Traveller> badges = HibernateService.getInstance().loadAll(Traveller.class);
		for (Traveller b : badges) {
			logger.info(b);
		}

		HibernateService.getInstance().shutdown();

	}

	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

}
