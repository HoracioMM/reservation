package controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.twilio.sdk.TwilioRestException;

import controller.service.EmailService;
import controller.service.HibernateService;
import controller.service.SMSService;
import controller.service.UserAuthenticationService;
import model.Account;
import model.Admin;
import model.Badge;
import model.Qr;
import model.Traveller;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

@RequestMapping(value = { "" })
@Controller
public class LoginController {

	private static Logger logger = Logger.getLogger(LoginController.class);

	/**
	 * Basic Login by username and password
	 * 
	 * @param username
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "login-native", method = RequestMethod.POST)
	public ModelAndView nativeLogin(@RequestParam String username, @RequestParam String password,
			HttpServletRequest request) {

		request.getSession().invalidate();
		// if username and password are correct
		if (UserAuthenticationService.getInstance().checkValidUser(username, password)) {
			logger.info("valid user to log in!");

			// set session parameters
			request.getSession().setAttribute("username", username);
			request.getSession().setAttribute("password", password);

			// record user ip and login time user: account
			Traveller traveller = HibernateService.getInstance().getUserByUserName(username);
			Account userAccount;
			if (traveller.getAccount() != null) {
				userAccount = traveller.getAccount();
			} else {
				userAccount = new Account();
				traveller.setAccount(userAccount);
			}

			userAccount.setDate(new Date());
			userAccount.setIpAddress(request.getRemoteAddr());

			HibernateService.getInstance().saveOrUpdate(userAccount);
			HibernateService.getInstance().saveOrUpdate(traveller);
			// if user is admin
			if (username.equalsIgnoreCase("admin")) {
				// serve admin page
				return new ModelAndView("admin-main");
			} else {
				// show paying page
				return new ModelAndView("pay").addObject("message",
						"Welcome " + request.getSession().getAttribute("username"));
			}

		} else {
			// redirect to login page
			return new ModelAndView("member_login").addObject("error",
					"Invalid username or password! Please Try again!");
		}
	}

	@RequestMapping(value = "login-id", method = RequestMethod.POST)
	public ModelAndView idLogin() {
		return null;
	}

	@RequestMapping(value = "create_user", method = RequestMethod.POST)
	public ModelAndView createUser(@ModelAttribute Traveller traveller) {
		HibernateService.getInstance().save(traveller);
		return null;
	}

	@RequestMapping(value = "login-passport", method = RequestMethod.POST)
	public void passportLogin() {

	}

	/**
	 * Register with mobile number and login
	 * 
	 * @param username
	 * @param street
	 * @param city
	 * @param country
	 * @param flightName
	 * @param travelDate
	 * @param visitingDate
	 * @param mobileNr
	 * @param check_email
	 * @param check_mobile
	 * @param request
	 * @return
	 * @throws TwilioRestException
	 */
	@RequestMapping(value = "mobile_reg", method = RequestMethod.POST)
	public ModelAndView mobileNumberLogin(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String username, @RequestParam String street,
			@RequestParam String city, @RequestParam String country, @RequestParam String flightName,
			@RequestParam String travelDate, @RequestParam String visitingDate, @RequestParam String mobileNr,
			@RequestParam String email, @RequestParam(required = false) String check_email,
			@RequestParam(required = false) String check_mobile, HttpServletRequest request)
			throws TwilioRestException {

		Random random = new Random();
		Traveller traveller = new Traveller();
		traveller.setFirstName(firstName);
		traveller.setLastName(lastName);	
		traveller.setUsername(username);
		traveller.setStreet(street);
		traveller.setCity(city);
		traveller.setCountry(country);
		traveller.setFlightName(flightName);
		traveller.setTravelDate(travelDate);
		traveller.setVisitingDate(visitingDate);
		traveller.setMobileNr(mobileNr);
		traveller.setEmail(email);

		int smscode = random.nextInt(1000000);

		traveller.setSmsCode(Integer.toString(smscode));
		traveller.setPassword(Integer.toString(smscode));

		HibernateService.getInstance().saveOrUpdate(traveller);

		logger.info("user " + username + " registered");
		SMSService.getInstance().sendSms(mobileNr,
				"Brussels Airlines Service \nYour Confirmation code is : " + Integer.toString(smscode));

		request.getSession().setAttribute("smscode", Integer.toString(smscode));
		request.getSession().setAttribute("username", username);
		request.getSession().setAttribute("password", traveller.getPassword());

		// notified by email
		if (check_email != null) {
			EmailService.getInstance().sendMail(email,
					"Congratulations! \nYou have successfully registerd with the Brussels Airlines Service!");
		}

		// notified by sms
		if (check_mobile != null) {
			SMSService.getInstance().sendSms(mobileNr,
					"Congratulations! \nYou have successfully registerd with the Brussels Airlines Service!");
		}

		return new ModelAndView("sms").addObject("error", "enter code received to : " + mobileNr);

	}

	/**
	 * verify sms-code and redirect to paying page
	 * 
	 * @param smscode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "sms", method = RequestMethod.POST)
	public ModelAndView smsLogin(@RequestParam String smscode, HttpServletRequest request) {

		Traveller user = HibernateService.getInstance()
				.getUserByUserName((String) request.getSession().getAttribute("username"));
		if (user != null) {

			if (user.getSmsCode().equalsIgnoreCase(smscode)) {
				return new ModelAndView("pay").addObject("message", "Welcome " + user.getUsername());
			} else {
				new ModelAndView("sms").addObject("error", "Invaild SMS code. Please Try Again!");
			}
			return new ModelAndView("pay").addObject("message", "Welcome " + user.getUsername());

		} else {
			return new ModelAndView("index").addObject("error", "Login failed! Try again!");
		}

	}

	/**
	 * Used to Badge login
	 * 
	 * @param username
	 * @param password
	 * @param badge_no
	 * @param profile
	 * @param request
	 * @param response
	 * @return if user found :Qr code Image; else go back to login page
	 * @throws IOException
	 */
	@RequestMapping(value = "login-badge", method = RequestMethod.POST)
	public ModelAndView badgeLogin(@RequestParam String username, @RequestParam String password,
			@RequestParam String badge_no, @RequestParam String profile, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		Badge badge = HibernateService.getInstance().getBadge(profile, username, password, badge_no);

		if (badge != null) {
			request.getSession().setAttribute("username", username);
			request.getSession().setAttribute("password", password);

			String qrtext = !HibernateService.getInstance().getWantedPerson(badge.getBadgeId())
					? username + " : " + "allowed" : username + " : " + "Wanted Person!";

			ByteArrayOutputStream out = QRCode.from(qrtext).to(ImageType.PNG).stream();

			response.setContentType("image/png");
			response.setContentLength(out.size());
			response.setHeader("Content-Disposition", "attachment; filename=qr.png");

			OutputStream outStream = response.getOutputStream();
			outStream.write(out.toByteArray());
			outStream.flush();
			outStream.close();
		} else {
			logger.info("user not found");
			return new ModelAndView("badge_login").addObject("error", "User not Found!");
		}
		return null;
	}

	@RequestMapping(value = "admin-main", method = RequestMethod.POST)
	public ModelAndView adminLogin(@RequestParam String username, @RequestParam String password,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		Admin admin = HibernateService.getInstance().getAdmin(username, password);

		if (admin.getUsername() != null && admin.getPassword() != null) {
			request.getSession().setAttribute("username", username);
			request.getSession().setAttribute("password", password);

			return new ModelAndView("admin-main");

		} else {
			logger.info("user not found");
			return new ModelAndView("index").addObject("error", "Admin not Found!");
		}

	}

	/**
	 * Issue QR code for user while checking the wanted people list
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "issue-qr", method = RequestMethod.POST)
	public ModelAndView issueQrAction(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String username = (String) request.getSession().getAttribute("username");
		String password = (String) request.getSession().getAttribute("password");

		// check if logged in or not
		if (username != null && password != null) {

			// get the related traveler
			Traveller user = HibernateService.getInstance().getUser(username, password);

			if (user != null) {

				int credits = user.getAccount().getCredits();

				// charge credits
				if (credits > 0) {
					logger.info("reducing credits by 1 for : " + username);
					user.getAccount().setCredits(credits - 1);
				} else {
					logger.info("charging 5 credits from the user : " + username);
					user.getAccount().setCredits(5);
				}
				HibernateService.getInstance().saveOrUpdate(user.getAccount());
				HibernateService.getInstance().saveOrUpdate(user);

				// set users qr
				// if not wanted : allowed
				if (!UserAuthenticationService.getInstance().checkWantedPerson(username)) {
					// issue Qr

					Qr qr = new Qr();
					qr.setAlarmTrigger("allowed");
					user.setQr(qr);

					HibernateService.getInstance().saveOrUpdate(qr);
					HibernateService.getInstance().saveOrUpdate(user);
					logger.info("QR issuing for : " + username + " with qr= " + qr.toString());

				} else {
					// if wanted : alarm qr
					Qr qr = new Qr();
					qr.setAlarmTrigger("Wanted Person");
					user.setQr(qr);

					HibernateService.getInstance().saveOrUpdate(qr);
					HibernateService.getInstance().saveOrUpdate(user);
					logger.info("Wanted QR issuing for : " + username + " with qr= " + qr.toString());
				}

				// create qr and download
				String qrtext = username + " : " + user.getQr().getAlarmTrigger();
				ByteArrayOutputStream out = QRCode.from(qrtext).to(ImageType.PNG).stream();

				response.setContentType("image/png");
				response.setContentLength(out.size());
				response.setHeader("Content-Disposition", "attachment; filename=qr.png");

				OutputStream outStream = response.getOutputStream();
				outStream.write(out.toByteArray());
				outStream.flush();
				outStream.close();
				// return response.get;

			} else {
				logger.info("user not found");
				return new ModelAndView("index").addObject("error", "User not Found!");
			}

		} else {
			logger.info("Please log in before proceed!");
			return new ModelAndView("index").addObject("error", "Please log in before proceed!");
		}
		return null;
	}

}
