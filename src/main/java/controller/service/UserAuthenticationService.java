package controller.service;

import model.Traveller;

/**
 * Singleton class for User authentication services
 *
 */
public class UserAuthenticationService {

	private static class InstanceHolder {
		static UserAuthenticationService INSTANCE = new UserAuthenticationService();
	}

	public static UserAuthenticationService getInstance() {
		return InstanceHolder.INSTANCE;
	}

	private UserAuthenticationService() {
	}

	/**
	 * Check for valid user by username and password
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean checkValidUser(String username, String password) {

		Traveller user = HibernateService.getInstance().getUser(username, password);
		if (user != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check user is wanted by username
	 * 
	 * @param username
	 * @return
	 */
	public boolean checkWantedPerson(String username) {

		Traveller user = HibernateService.getInstance().getUserByUserName(username);
		return HibernateService.getInstance().getWantedPerson(user.getTravellerId());
	}

}
