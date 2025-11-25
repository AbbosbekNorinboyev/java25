package uz.brb.java25.util.validator;

import module java.base;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExpiryDateValidator implements ConstraintValidator<ExpiryDate, String> {

    private static final String DATE_FORMAT = "yyMM";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // Null or empty values are considered valid as @NotNull is used separately
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setLenient(false); // Disable lenient parsing

        try {
            Date parsedDate = dateFormat.parse(value);
            return parsedDate != null;
        } catch (ParseException e) {
            return false;
        }
    }
}