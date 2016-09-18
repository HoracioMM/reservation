package reservation.test;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.twilio.sdk.TwilioRestException;

import controller.service.HibernateService;
import model.Country;
import model.Flight;

public class AddNewCoutries {

	private static Logger logger = Logger.getLogger(AddNewCoutries.class);

	public static void main(String[] args) throws TwilioRestException {
		List<Country> countries = new LinkedList<>();
		Country c;
		try {
			c = new Country();
			c.setName("France");
			countries.add(c);

			c = new Country();
			c.setName("Spain");
			countries.add(c);

			c = new Country();
			c.setName("Gemany");
			countries.add(c);

			List<Flight> flights = new LinkedList<>();
			Flight f;

			f = new Flight();
			f.setName("Ryanair");
			flights.add(f);

			f = new Flight();
			f.setName("Air France");
			flights.add(f);
			HibernateService.getInstance().save(flights);

		} catch (Exception e) {
			logger.error(e);

		}
		HibernateService.getInstance().save(countries);
		HibernateService.getInstance().shutdown();

	}

}
