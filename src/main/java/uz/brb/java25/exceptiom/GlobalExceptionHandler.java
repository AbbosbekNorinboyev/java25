package uz.brb.java25.exceptiom;

import module java.base;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import uz.brb.java25.dto.response.Response;

import static uz.brb.java25.util.Util.localDateTimeFormatter;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // dto va request validatsiya xatolari
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<?> exception(MethodArgumentNotValidException e,
                                 HttpServletRequest request) {
        Map<String, String> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        DefaultMessageSourceResolvable::getDefaultMessage));
        return Response.builder()
                .code(HttpStatus.BAD_REQUEST.value())  // Bad request kodi
                .status(HttpStatus.BAD_REQUEST)
                .message("Validation error")
                .errors(errors)
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
    }

    // 400 - Parametr validatsiya
    @ExceptionHandler(ConstraintViolationException.class)
    public Response<?> handleConstraintViolation(ConstraintViolationException ex,
                                                 HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(v ->
                errors.put(v.getPropertyPath().toString(), v.getMessage())
        );
        return Response.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST)
                .message("Validation failed")
                .success(false)
                .errors(errors)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
    }

    // 400 - Noto‘g‘ri argument yuborilgan
    @ExceptionHandler(IllegalArgumentException.class)
    public Response<?> handleIllegalArgumentException(IllegalArgumentException ex,
                                                      HttpServletRequest request) {
        return Response.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST)
                .message("Invalid argument: " + ex.getMessage())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
    }

    // 400
    @ExceptionHandler(BadRequestException.class)
    public Response<?> handleBadRequestFoundException(BadRequestException badRequestException,
                                                      HttpServletRequest request) {
        return Response.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST)
                .message(badRequestException.getMessage())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
    }

    // 400
    @ExceptionHandler(InvalidHeadersException.class)
    public Response<?> handleInvalidHeaders(InvalidHeadersException ex, WebRequest request) {
        return Response.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .success(false)
                .timestamp(LocalDateTime.now().toString())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();
    }

    // 404 - Resource topilmadi
    @ExceptionHandler(ResourceNotFoundException.class)
    public Response<?> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException,
                                                       HttpServletRequest request) {
        return Response.builder()
                .code(HttpStatus.NOT_FOUND.value())  // Not found request kodi
                .status(HttpStatus.NOT_FOUND)
                .message(resourceNotFoundException.getMessage())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
    }

    // 405 - Noto'g'ri HTTP metod
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response<?> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                HttpServletRequest request) {
        return Response.builder()
                .code(HttpStatus.METHOD_NOT_ALLOWED.value())
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .message("Method not allowed: " + ex.getMethod())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
    }

    // 409 - DB constraint buzilgan (masalan duplicate key)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Response<?> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                                    HttpServletRequest request) {
        return Response.builder()
                .code(HttpStatus.CONFLICT.value())
                .status(HttpStatus.CONFLICT)
                .message("Conflict: " + ex.getRootCause().getMessage())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
    }

    // 500
    @ExceptionHandler(Exception.class)
    public Response<?> handleException(Exception exception,
                                       HttpServletRequest request) {
        return Response.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())   // Internal Server Error request kodi
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Something wrong -> " + exception.getMessage())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(JsonConversionException.class)
    public Response<?> handleJsonConversionException(JsonConversionException ex, HttpServletRequest request) {
        return Response.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("JSON conversion error: " + ex.getMessage())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
    }
}
