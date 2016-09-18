package controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controller.service.HibernateService;
import model.Country;
import model.Flight;
import model.Traveller;
import model.WantedPerson;

@RequestMapping(value = { "" })
@Controller
public class BaseController {

	private static Logger logger = Logger.getLogger(BaseController.class);

	/**
	 * Serve the index page
	 * 
	 * @return
	 */
	@RequestMapping(value = { "", "index" }, method = RequestMethod.GET)
	public ModelAndView indexAction() {
		logger.info("index page");
		return new ModelAndView("index");
	}

	@RequestMapping(value = "id_card_reg", method = RequestMethod.GET)
	public ModelAndView idCardRegAction() {
		return new ModelAndView("id_card_reg");
	}

	@RequestMapping(value = "passport_reg", method = RequestMethod.GET)
	public ModelAndView passportRegAction() {
		return new ModelAndView("passport_reg");
	}

	@RequestMapping(value = "mobile_reg", method = RequestMethod.GET)
	public ModelAndView mobileRegAction() {
		List<Country> countries = HibernateService.getInstance().loadAll(Country.class);
		List<Flight> flights = HibernateService.getInstance().loadAll(Flight.class);
		return new ModelAndView("mobile_reg").addObject("country", countries).addObject("flight", flights);
	}

	@RequestMapping(value = "badge_login", method = RequestMethod.GET)
	public ModelAndView badgeLoginAction() {
		return new ModelAndView("badge_login");
	}

	@RequestMapping(value = "member_login", method = RequestMethod.GET)
	public ModelAndView memberLoginAction() {
		return new ModelAndView("member_login");
	}

	@RequestMapping(value = "admin-main", method = RequestMethod.GET)
	public ModelAndView adminLoginAction(String username, String password) {
//		if (username.equals("Horacio") && password.equals("Intec Brussel")) {
//			return new ModelAndView("admin-main");
//		}
		return new ModelAndView("admin-main");

	}

	@RequestMapping(value = "wanted_list", method = RequestMethod.GET)
	public ModelAndView wantedList(HttpServletRequest request) {
		String name = request.getSession().getAttribute("username") == null ? ""
				: (String) request.getSession().getAttribute("username");
		if (name.equalsIgnoreCase("admin")) {
			// list all ids of wanted people
			List<WantedPerson> wanted = HibernateService.getInstance().loadAll(WantedPerson.class);
			List<Traveller> wantedTravelers = new LinkedList<>();
			// get all travelers related
			for (WantedPerson w : wanted) {
				wantedTravelers
						.add(HibernateService.getInstance().getObjectById(Traveller.class, w.getWantedPersonId()));
			}
			return new ModelAndView("wanted_list").addObject("wanted", wantedTravelers);
		} else {
			return memberLoginAction().addObject("error", "You must be admin to access this page!");
		}
	}

	@RequestMapping(value = "list-users", method = RequestMethod.GET)
	public ModelAndView listEditUsers(HttpServletRequest request) {
		String name = request.getSession().getAttribute("username") == null ? ""
				: (String) request.getSession().getAttribute("username");
		// if admin
		if (name.equalsIgnoreCase("admin")) {
			// list all ids of wanted people
			List<Traveller> travelers = HibernateService.getInstance().loadAll(Traveller.class);
			// get all travelers related
			return new ModelAndView("list-users").addObject("travelers", travelers);
		} else {
			return memberLoginAction().addObject("error", "You must be admin to access this page!");
		}
	}

}
