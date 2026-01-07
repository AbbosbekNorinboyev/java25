package uz.brb.java25.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import uz.brb.java25.exceptiom.InvalidHeadersException;
import uz.brb.java25.exceptiom.JsonConversionException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Set;

@Slf4j
public class Util {
    public static String convertObjectToJsonString(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JsonConversionException("Failed to convert object to JSON", e);
        }
        return jsonString;
    }

    public static void validate(Object validate) {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<Object>> violations = validator.validate(validate);
            if (!violations.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (ConstraintViolation<Object> violation : violations) {
                    String interpolatedMessage = violation.getMessage();
                    String propertyPath = violation.getPropertyPath().toString();
                    sb.append(propertyPath)
                            .append(": ")
                            .append(interpolatedMessage)
                            .append(System.lineSeparator());
                }
                throw new RuntimeException(sb.toString());
            }
        }
    }

    public static void validateHeader(Object validate, String errMessage) {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            var violations = validator.validate(validate);
            if (!violations.isEmpty()) {
                throw new InvalidHeadersException(errMessage);
            }
        }
    }

    public static String dateTimeFormatter(Date date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");

        return date.toInstant()
                .atZone(ZoneId.of("UTC+5"))
                .toLocalDateTime()
                .format(formatter);
    }

    public static String localDateTimeFormatter(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");

        return localDateTime
                .atZone(ZoneId.of("UTC+5"))
                .toLocalDateTime()
                .format(formatter);
    }

    public static Date convertToLocalDateTimeToDateWithMinutesAdded(int minute) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime utcPlus5DateTime = LocalDateTime.now()
                .plusMinutes(minute)
                .atZone(zoneId)
                .withZoneSameInstant(ZoneId.of("UTC+5"));
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            String formattedDateTime = utcPlus5DateTime.format(formatter);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            return dateFormat.parse(formattedDateTime);
        } catch (ParseException e) {
            if (log.isErrorEnabled()) {
                log.error("DATE_PARSING {}", e.getMessage());
            }
        }
        return Date.from(utcPlus5DateTime.toInstant());
    }

    public static Date convertToLocalDateTimeToDate() {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime utcPlus5DateTime = LocalDateTime.now()
                .atZone(zoneId)
                .withZoneSameInstant(ZoneId.of("UTC+5"));
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            String formattedDateTime = utcPlus5DateTime.format(formatter);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            return dateFormat.parse(formattedDateTime);
        } catch (ParseException e) {
            if (log.isErrorEnabled()) {
                log.error("DATE_PARSING {}", e.getMessage());
            }
        }
        return Date.from(utcPlus5DateTime.toInstant());
    }

    public static String convertToLocalDateTimeToString(String pattern) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime utcPlus5DateTime = LocalDateTime.now()
                .atZone(zoneId)
                .withZoneSameInstant(ZoneId.of("UTC+5"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return utcPlus5DateTime.format(formatter);
    }

    public static String convertToLocalDateTimePlusMonthToString(String pattern, int months, int plusDays) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime utcPlus5DateTime = LocalDateTime.now()
                .plusMonths(months)
                .plusDays(plusDays)
                .atZone(zoneId)
                .withZoneSameInstant(ZoneId.of("UTC+5"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return utcPlus5DateTime.format(formatter);
    }
}
