package lk.imms.management_system.util.service;

import org.springframework.stereotype.Service;

@Service
public class ExceptionService {
    public int stringToInt(String paramter) {
        int userEnteredNumber = 0;
        try {
            userEnteredNumber = Integer.parseInt(paramter);
        } catch (NumberFormatException ne) {
            System.out.println("You have entered wrong number");
        }
        return userEnteredNumber;
    }
}
