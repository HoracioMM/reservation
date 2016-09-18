package controller.service;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * Singleton Class for Emailing service
 *
 */
public class EmailService {

	private final String API_KEY = "key-3ax6xnjp29jd6fds4gc373sgvjxteol0";
	private final String CLIENT = "https://api.mailgun.net/v3/samples.mailgun.org/messages";
	private final String FROM = "Airline Notification System <excited@samples.mailgun.org>";
	private static Logger logger = Logger.getLogger(EmailService.class);

	private static class InstanceHolder {
		static EmailService INSTANCE = new EmailService();
	}

	public static EmailService getInstance() {
		return InstanceHolder.INSTANCE;
	}

	private EmailService() {
	}

	public void sendMail(String to, String message) {

		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("api", API_KEY));
		WebResource webResource = client.resource(CLIENT);

		MultivaluedMapImpl formData = new MultivaluedMapImpl();
		formData.add("from", FROM);
		formData.add("to", to);
		formData.add("subject", "Brussels Airline Notification");
		formData.add("text", message);

		client.destroy();
		logger.info("Email Sending Status response : "
				+ webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData));

	}

}
