package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controller.service.HibernateService;
import model.Country;
import model.Traveller;

@RequestMapping(value = { "" })
@Controller
public class UserController {

	private static Logger logger = Logger.getLogger(UserController.class);

	/**
	 * Delete user if user is admin
	 * 
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "delete-user", method = RequestMethod.GET)
	public ModelAndView deleteUsers(@RequestParam int user, HttpServletRequest request) {
		String name = request.getSession().getAttribute("username") == null ? ""
				: (String) request.getSession().getAttribute("username");
		// if admin
		if (name.equalsIgnoreCase("admin")) {
			Traveller traveller = HibernateService.getInstance().getObjectById(Traveller.class, user);
			HibernateService.getInstance().delete(traveller);
			// list all ids of all travelers
			List<Traveller> travelers = HibernateService.getInstance().loadAll(Traveller.class);
			// get all travelers related
			return new ModelAndView("list-users").addObject("travelers", travelers);
		} else {
			logger.info("Try to non admin access");
			return new ModelAndView("member-login").addObject("error", "You must be admin to access this page!");
		}
	}

	/**
	 * Serve edit user page if admin
	 * 
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "edit-user", method = RequestMethod.GET)
	public ModelAndView editUsers(@RequestParam int user, HttpServletRequest request) {
		String name = request.getSession().getAttribute("username") == null ? ""
				: (String) request.getSession().getAttribute("username");
		// if admin
		if (name.equalsIgnoreCase("admin")) {
			Traveller traveller = HibernateService.getInstance().getObjectById(Traveller.class, user);
			List<Country> country = HibernateService.getInstance().loadAll(Country.class);
			return new ModelAndView("edit-user").addObject("user", traveller).addObject("country", country);
		} else {
			logger.info("Try to non admin access");
			return new ModelAndView("member_login").addObject("error", "You must be admin to access this page!");
		}
	}

	/**
	 * Edit the user specified by : @param user
	 * 
	 * @param user
	 * @param firstName
	 * @param lastName
	 * @param city
	 * @param country
	 * @param email
	 * @param idNumber
	 * @param passport
	 * @param mobile
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "edit-user", method = RequestMethod.POST)
	public ModelAndView editUserAction(@RequestParam int user, @RequestParam String firstName,
			@RequestParam String lastName, @RequestParam String city, @RequestParam String country,
			@RequestParam String email, @RequestParam String idNumber, @RequestParam String passport,
			@RequestParam String mobile, HttpServletRequest request) {

		String name = request.getSession().getAttribute("username") == null ? ""
				: (String) request.getSession().getAttribute("username");
		// if admin
		if (name.equalsIgnoreCase("admin")) {
			Traveller traveler = HibernateService.getInstance().getObjectById(Traveller.class, user);
			traveler.setFirstName(firstName);
			traveler.setLastName(lastName);
			traveler.setCity(city);
			traveler.setCountry(country);
			traveler.setEmail(email);
			traveler.setIdCardNr(idNumber);
			traveler.setPasportNr(passport);
			traveler.setMobileNr(mobile);

			logger.info("Updating user as: " + traveler.getUsername());
			HibernateService.getInstance().saveOrUpdate(traveler);
			return new ModelAndView("edit-user").addObject("user", traveler).addObject("error",
					"User Updated Successfully!");
		} else {
			logger.info("Try to non admin access");
			return new ModelAndView("member-login").addObject("error", "You must be admin to access this page!");
		}
	}

	/**
	 * Serve Add new User page
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "add-user", method = RequestMethod.GET)
	public ModelAndView addUsers(HttpServletRequest request) {
		String name = request.getSession().getAttribute("username") == null ? ""
				: (String) request.getSession().getAttribute("username");
		// if admin
		if (name.equalsIgnoreCase("admin")) {
			List<Country> country = HibernateService.getInstance().loadAll(Country.class);
			return new ModelAndView("add-user").addObject("country", country);
		} else {
			logger.info("Try to non admin access");
			return new ModelAndView("member_login").addObject("error", "You must be admin to access this page!");
		}
	}

	/**
	 * Add new user action
	 * 
	 * @param user
	 * @param firstName
	 * @param lastName
	 * @param city
	 * @param country
	 * @param email
	 * @param idNumber
	 * @param passport
	 * @param mobile
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "add-user", method = RequestMethod.POST)
	public ModelAndView addUserAction(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String city, @RequestParam String country, @RequestParam String email,
			@RequestParam String idNumber, @RequestParam String passport, @RequestParam String mobile,
			HttpServletRequest request) {

		String name = request.getSession().getAttribute("username") == null ? ""
				: (String) request.getSession().getAttribute("username");
		// if admin
		if (name.equalsIgnoreCase("admin")) {
			Traveller user = new Traveller();
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setCity(city);
			user.setCountry(country);
			user.setEmail(email);
			user.setIdCardNr(idNumber);
			user.setPasportNr(passport);
			user.setMobileNr(mobile);

			logger.info("Add new user as: " + user.toString());
			// HibernateService.getInstance().saveOrUpdate(user);
			return new ModelAndView("edit-user").addObject("user", user).addObject("error", "User Added Successfully!");
		} else {
			logger.info("Try to non admin access");
			return new ModelAndView("member-login").addObject("error", "You must be admin to access this page!");
		}
	}

}
