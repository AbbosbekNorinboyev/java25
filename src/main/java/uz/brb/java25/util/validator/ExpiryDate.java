package uz.brb.java25.util.validator;

import module java.base;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = ExpiryDateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExpiryDate {

    String message() default "Invalid date format. Use yyMM";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}