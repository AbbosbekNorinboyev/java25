package uz.brb.java25.util.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CardNumberValidator implements ConstraintValidator<CardNumber, String> {

    @Override
    public void initialize(CardNumber constraintAnnotation) {
    }

    @Override
    public boolean isValid(String number, ConstraintValidatorContext context) {
        if (number == null || number.isEmpty()) {
            return true; // Null or empty values are considered valid as @NotNull is used separately
        }
        int sum = 0;
        boolean alternate = false;

        for (int i = number.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(number.charAt(i));

            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }

            sum += digit;
            alternate = !alternate;
        }

        return (sum % 10 == 0);
    }
}