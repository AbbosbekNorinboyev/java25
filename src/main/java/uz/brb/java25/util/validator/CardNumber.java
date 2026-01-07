package uz.brb.java25.util.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CardNumberValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CardNumber {

    String message() default "The card number is not valid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}