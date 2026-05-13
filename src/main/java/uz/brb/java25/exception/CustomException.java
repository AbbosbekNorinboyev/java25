package uz.brb.java25.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private final HttpStatus status;

    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public CustomException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public static CustomException notFound(String message) {
        return new CustomException(message, HttpStatus.NOT_FOUND);
    }

    public static CustomException userNotFound(String message) {
        return new CustomException(message, HttpStatus.NOT_FOUND);
    }

    public static CustomException badRequest(String message) {
        return new CustomException(message, HttpStatus.BAD_REQUEST);
    }

    public static CustomException jsonConversion(String message, Throwable cause) {
        return new CustomException(message, cause, HttpStatus.BAD_REQUEST);
    }

    public static CustomException invalidHeaders(String message) {
        return new CustomException(message, HttpStatus.BAD_REQUEST);
    }

    public static CustomException invalidHeaders(String message, Throwable cause) {
        return new CustomException(message, cause, HttpStatus.BAD_REQUEST);
    }

    public static CustomException conflict(String message) {
        return new CustomException(message, HttpStatus.CONFLICT);
    }

    public static CustomException forbidden(String message) {
        return new CustomException(message, HttpStatus.FORBIDDEN);
    }

    public static CustomException unauthorized(String message) {
        return new CustomException(message, HttpStatus.UNAUTHORIZED);
    }

    // TOKEN / AUTH xatolar
    public static CustomException tokenExpired(String message) {
        return new CustomException(message, HttpStatus.UNAUTHORIZED);
    }

    public static CustomException invalidToken(String message) {
        return new CustomException(message, HttpStatus.UNAUTHORIZED);
    }

    // ACCESS DENIED (FORBIDDEN kengaytmasi)
    public static CustomException accessDenied(String message) {
        return new CustomException(message, HttpStatus.FORBIDDEN);
    }

    // INTERNAL ERROR (fallback)
    public static CustomException internalError(String message) {
        return new CustomException(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static CustomException internalError(String message, Throwable cause) {
        return new CustomException(message, cause, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // EXTERNAL SERVICE ERROR (microservice uchun MUHIM)
    public static CustomException externalService(String message) {
        return new CustomException(message, HttpStatus.BAD_GATEWAY);
    }

    public static CustomException externalService(String message, Throwable cause) {
        return new CustomException(message, cause, HttpStatus.BAD_GATEWAY);
    }

    // DATA INTEGRITY / DB ERROR
    public static CustomException dataIntegrity(String message) {
        return new CustomException(message, HttpStatus.CONFLICT);
    }

    // NOT ALLOWED / BUSINESS RULE
    public static CustomException businessRule(String message) {
        return new CustomException(message, HttpStatus.BAD_REQUEST);
    }
}
