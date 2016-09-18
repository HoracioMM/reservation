package reservation.test;

import org.apache.log4j.Logger;

import controller.service.HibernateService;
import controller.service.UserAuthenticationService;
import model.Qr;
import model.Traveller;
import model.WantedPerson;

public class QrTest {

	private static Logger logger = Logger.getLogger(QrTest.class);

	public static void main(String[] args) {

		Traveller user = new Traveller();
		user.setUsername("user" + Double.toString(Math.random()));
		user.setPassword(user.getUsername());
		HibernateService.getInstance().saveOrUpdate(user);
		logger.info(user.getTravellerId());

		WantedPerson wantedPerson = new WantedPerson();
		wantedPerson.setWantedPersonId(user.getTravellerId());
		HibernateService.getInstance().saveOrUpdate(wantedPerson);

		issueQr(user.getUsername(), user.getPassword());
		issueQr(user.getUsername() + "12345", user.getPassword());

		HibernateService.getInstance().shutdown();
	}

	private static void issueQr(String username, String password) {
		if (UserAuthenticationService.getInstance().checkValidUser(username, password)) {

			Traveller user = HibernateService.getInstance().getUserByUserName(username);
			Qr qr;
			if (user.getQr() == null) {
				qr = new Qr();
				user.setQr(qr);
				qr.setAlarmTrigger("allowed");
				HibernateService.getInstance().saveOrUpdate(qr);
				// HibernateService.getInstance().saveOrUpdate(user);
			}

			if (!UserAuthenticationService.getInstance().checkWantedPerson(username)) {
				// issue Qr
				user.getQr().setAlarmTrigger("allowed");
				logger.info("QR issuing!");

			} else {
				// alarm qr
				user.getQr().setAlarmTrigger("wanted person");
				logger.info("Wanted persson");
			}
		} else {
			logger.info("user not found");
		}
	}

}
