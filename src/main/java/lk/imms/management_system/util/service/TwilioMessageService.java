package lk.imms.management_system.util.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import org.springframework.stereotype.Service;

@Service
public class TwilioMessageService {
    // Find your Account Sid and Token at twilio.com/user/account
    public static final String ACCOUNT_SID = "AC5ef872f6da5a21de157d80997a64bd33";
    public static final String AUTH_TOKEN = "your_auth_token";


    public void sendSMS(){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message
                .creator(new PhoneNumber("+16518675309"), new PhoneNumber("+14158141829"),
                         "Tomorrow's forecast in Financial District, San Francisco is Clear")
                .setMediaUrl("https://climacons.herokuapp.com/clear.png")
                .create();

        /*
        * Old technology
        * String ACCOUNT_SID = "AC5eb2291fc32feefffaee1e72124744c8";
        String AUTH_TOKEN ="3ad8aa83519e4a57147810b324f4fca2";
        String myTwilioNumber = "+15067006522";
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
       Message.creator(new PhoneNumber(senderNumber), new PhoneNumber(myTwilioNumber), message).create();
        *
        * */
    }
}