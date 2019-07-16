package lk.imms.management_system.util.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
@Service
public class OperatorService {

    public  BigDecimal multiply(BigDecimal operand1, BigDecimal operand2) {
        return operand1.multiply(operand2).setScale(2, RoundingMode.CEILING);
    }

    public  BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
        return dividend.divide(divisor, 2, RoundingMode.CEILING);
    }

    public  BigDecimal addition(BigDecimal operand1, BigDecimal operand2) {
        return operand1.add(operand2).setScale(2, RoundingMode.CEILING);
    }

    public  BigDecimal subtraction(BigDecimal operand1, BigDecimal operand2) {
        return operand1.subtract(operand2).setScale(2, RoundingMode.CEILING);
    }

    public  BigDecimal pow(BigDecimal operand1, int operand2) {
        return operand1.pow(operand2).setScale(2, RoundingMode.CEILING);
    }
}