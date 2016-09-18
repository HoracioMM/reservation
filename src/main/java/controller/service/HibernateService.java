package controller.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import model.Admin;
import model.Badge;
import model.Traveller;
import model.WantedPerson;

/**
 * Singleton Class for MySQL db access with Hibernate
 *
 */
public class HibernateService {

	private final SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(HibernateService.class);

	private static class InstanceHolder {
		static HibernateService INSTANCE = new HibernateService();
	}

	public static HibernateService getInstance() {
		return InstanceHolder.INSTANCE;
	}

	private HibernateService() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}

	public void init(String basePath) {

	}

	/**
	 * Save an new {@link Entity} objects
	 * 
	 * @param object
	 * @return
	 */
	public Serializable save(Object object) {
		Session dbSession = sessionFactory.getCurrentSession();
		org.hibernate.Transaction transaction = dbSession.beginTransaction();

		try {
			Serializable result = dbSession.save(object);
			transaction.commit();

			return result;
		} catch (Exception e) {
			logger.error(e);
			transaction.rollback();
			return null;
		}
	}

	/**
	 * Save or update {@link Entity} objects
	 * 
	 * @param object
	 */
	public void saveOrUpdate(Object object) {
		Session dbSession = sessionFactory.getCurrentSession();
		Transaction transaction = dbSession.beginTransaction();

		try {
			dbSession.saveOrUpdate(object);
			transaction.commit();

		} catch (Exception e) {
			logger.error(e);
			transaction.rollback();

		}
	}

	/**
	 * delete
	 * 
	 * @param object
	 */
	public void delete(Object object) {
		Session dbSession = sessionFactory.getCurrentSession();
		Transaction transaction = dbSession.beginTransaction();

		try {
			dbSession.delete(object);
			transaction.commit();

		} catch (Exception e) {
			logger.error(e);
			transaction.rollback();

		}
	}

	/**
	 * Save list of {@link Entity} objects
	 * 
	 * @param list
	 * @return
	 */
	public <T> List<Serializable> save(List<T> list) {
		Session dbSession = sessionFactory.getCurrentSession();
		Transaction transaction = dbSession.beginTransaction();
		List<Serializable> results = new ArrayList<>();
		try {
			for (T object : list) {
				results.add(dbSession.save(object));
			}

			transaction.commit();

			return results;
		} catch (Exception e) {
			logger.error(e);
			transaction.rollback();

			return results;
		}
	}

	/**
	 * Load all {@link Entity} s from the given table
	 * 
	 * @param type
	 * @return
	 */
	public <T> List<T> loadAll(Class<T> type) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(type);
			@SuppressWarnings("unchecked")
			List<T> resultList = criteria.list();

			transaction.commit();
			return resultList;
		} catch (Exception e) {
			logger.error(e);
			transaction.rollback();
			return null;
		}
	}

	/**
	 * Shutdown the db service
	 */
	public void shutdown() {
		if (sessionFactory != null) {
			sessionFactory.getCurrentSession().close();
			sessionFactory.close();
		}
	}

	/**
	 * Retrive user when username and password given
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public Traveller getUser(String username, String password) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(Traveller.class).add(Restrictions.eq("username", username))
					.add(Restrictions.eq("password", password));
			@SuppressWarnings("unchecked")
			List<Traveller> users = criteria.list();
			transaction.commit();

			if (users.size() == 1) {
				logger.info("User : " + username + " found in the System");
				return users.get(0);
			} else {
				logger.error("no users found in the System!");
				return null;
			}

		} catch (Exception e) {
			logger.error(e);
			transaction.rollback();
			return null;
		}
	}

	/**
	 * Retrive user when username and password given
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public Traveller getUserByUserName(String username) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(Traveller.class).add(Restrictions.eq("username", username));
			@SuppressWarnings("unchecked")
			List<Traveller> users = criteria.list();
			transaction.commit();

			if (users.size() == 1) {
				logger.info("User : " + username + " found in the System");
				return users.get(0);
			} else {
				logger.error("no users found in the System!");
				return null;
			}

		} catch (Exception e) {
			logger.error(e);
			transaction.rollback();
			return null;
		}
	}

	/**
	 * Check if the user is in the wanted people table
	 * 
	 * 
	 * @param travellerId
	 * @return true if person is wanted
	 */
	public boolean getWantedPerson(int travellerId) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(WantedPerson.class).add(Restrictions.idEq(travellerId));
			@SuppressWarnings("unchecked")
			List<WantedPerson> users = criteria.list();
			transaction.commit();

			if (users.size() == 1) {
				return true;
			} else {
				logger.error("not a wanted person!");
				return false;
			}

		} catch (Exception e) {
			logger.error(e);
			transaction.rollback();
			return false;
		}
	}

	public Badge getBadge(String userType, String username, String password, String badge_no) {

		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(Traveller.class).createAlias("userProfile", "up")
					.createAlias("up.badge", "upb").add(Restrictions.eq("username", username))
					.add(Restrictions.eq("password", password)).add(Restrictions.eq("up.userType", userType))
					.add(Restrictions.eq("upb.badgeNr", badge_no));

			List<?> badgeUsers = criteria.list();
			transaction.commit();

			if (badgeUsers.size() == 1) {
				return ((Traveller) badgeUsers.get(0)).getUserProfile().getBadge();
			} else {
				logger.error("User not found! Please Try again!");
				return null;
			}

		} catch (Exception e) {
			logger.error(e);
			transaction.rollback();
			return null;
		}
	}

	public Admin getAdmin(String username, String password) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(Admin.class).createAlias("adminProfile", "up")
					.createAlias("up.admin", "upad").add(Restrictions.eq("username", username))
					.add(Restrictions.eq("password", password));

			List<?> allAdmin = criteria.list();
			transaction.commit();

			if (allAdmin.size() == 1) {
				return ((Admin) allAdmin.get(0));
			} else {
				logger.error("Admin not found! Please Try again!");
				return null;
			}

		} catch (Exception e) {
			logger.error(e);
			transaction.rollback();
			return null;
		}
	}

	public <T> T getObjectById(Class<T> type, int id) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(type).add(Restrictions.idEq(id));

			@SuppressWarnings("unchecked")
			List<T> list = criteria.list();
			transaction.commit();
			if (list.size() == 1) {
				return list.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error(e);
			transaction.rollback();
			return null;
		}
	}

}
