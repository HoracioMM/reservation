package reservation.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

public class SMSTest {

	private final static String ACCOUNT_SID = "ACbba3dd1f4bf55f39dd6c2ac36efd7411";
	private final static String AUTH_TOKEN = "1ca52f79face6d76d719ec9716e54aa7";

	private static Logger logger = Logger.getLogger(SMSTest.class);

	public static void main(String[] args) throws TwilioRestException {
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

		// Build the parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("To", "+32479822932"));
		params.add(new BasicNameValuePair("From", "+18442703205"));
		params.add(new BasicNameValuePair("Body", "Hi Horacio"));

		MessageFactory messageFactory = client.getAccount().getMessageFactory();
		Message message = messageFactory.create(params);
		logger.info(message.getSid());
	}

}
