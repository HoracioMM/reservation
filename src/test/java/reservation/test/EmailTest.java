package reservation.test;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class EmailTest {

	public static void main(String[] args) {

		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("api", "key-3ax6xnjp29jd6fds4gc373sgvjxteol0"));
		WebResource webResource = client.resource("https://api.mailgun.net/v3/samples.mailgun.org/messages");
		MultivaluedMapImpl formData = new MultivaluedMapImpl();
		formData.add("from", "Excited User <excited@samples.mailgun.org>");
		formData.add("to", "hmm.programming@gmail.com");
		formData.add("subject", "Hello");
		formData.add("text", "Congratulations you registered to gain access to Brussels Airport !");
		System.out
				.println(webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData));
	}

}
