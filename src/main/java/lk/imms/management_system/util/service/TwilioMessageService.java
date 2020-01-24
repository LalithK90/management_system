package lk.imms.management_system.util.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;


@Service
public class TwilioMessageService {
    // Find your Account Sid and Token at twilio.com/user/account
    public static final String ACCOUNT_SID = "AC6d2afa0374c991be0ea662f1b11bba01";
    public static final String AUTH_TOKEN = "34381f51b977ee369654d66986be2ab0";


    public void sendSMS(String number, String messageBody) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message
                .creator(new PhoneNumber(number), new PhoneNumber("+19386666123"),
                         messageBody)
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

        System.out.println("Message "+message.getSid());
    }
}