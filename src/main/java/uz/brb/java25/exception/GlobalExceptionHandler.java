package uz.brb.java25.exception;

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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static uz.brb.java25.util.Util.localDateTimeFormatter;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // dto va request validatsiya xatolari
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<?> exception(MethodArgumentNotValidException e,
                                 HttpServletRequest req) {
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
                .path(req.getRequestURI())
                .build();
    }

    // 400 - Parametr validatsiya
    @ExceptionHandler(ConstraintViolationException.class)
    public Response<?> handleConstraintViolation(ConstraintViolationException ex,
                                                 HttpServletRequest req) {
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
                .path(req.getRequestURI())
                .build();
    }

    // 400 - Noto‘g‘ri argument yuborilgan
    @ExceptionHandler(IllegalArgumentException.class)
    public Response<?> handleIllegalArgumentException(IllegalArgumentException ex,
                                                      HttpServletRequest req) {
        return Response.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST)
                .message("Invalid argument: " + ex.getMessage())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(req.getRequestURI())
                .build();
    }

    // 400
    @ExceptionHandler(BadRequestException.class)
    public Response<?> handleBadRequestFoundException(BadRequestException ex, HttpServletRequest rqe) {
        return Response.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(rqe.getRequestURI())
                .build();
    }

    // 400
    @ExceptionHandler(InvalidHeadersException.class)
    public Response<?> handleInvalidHeaders(InvalidHeadersException ex, WebRequest req) {
        return Response.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .success(false)
                .timestamp(LocalDateTime.now().toString())
                .path(((ServletWebRequest) req).getRequest().getRequestURI())
                .build();
    }

    // 404 - Resource topilmadi
    @ExceptionHandler(ResourceNotFoundException.class)
    public Response<?> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                       HttpServletRequest req) {
        return Response.builder()
                .code(HttpStatus.NOT_FOUND.value())  // Not found request kodi
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(req.getRequestURI())
                .build();
    }

    // 405 - Noto'g'ri HTTP metod
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response<?> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                HttpServletRequest req) {
        return Response.builder()
                .code(HttpStatus.METHOD_NOT_ALLOWED.value())
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .message("Method not allowed: " + ex.getMethod())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(req.getRequestURI())
                .build();
    }

    // 409 - DB constraint buzilgan (masalan duplicate key)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Response<?> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                                    HttpServletRequest req) {
        return Response.builder()
                .code(HttpStatus.CONFLICT.value())
                .status(HttpStatus.CONFLICT)
                .message("Conflict: " + ex.getRootCause().getMessage())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(req.getRequestURI())
                .build();
    }

    // 500
    @ExceptionHandler(Exception.class)
    public Response<?> handleException(Exception ex, HttpServletRequest req) {
        return Response.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())   // Internal Server Error request kodi
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Something wrong -> " + ex.getMessage())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(req.getRequestURI())
                .build();
    }

    @ExceptionHandler(JsonConversionException.class)
    public Response<?> handleJsonConversionException(JsonConversionException ex, HttpServletRequest req) {
        return Response.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("JSON conversion error: " + ex.getMessage())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(req.getRequestURI())
                .build();
    }

    // Custom exception
    @ExceptionHandler(CustomException.class)
    public Response<?> handleCustomException(CustomException ex, HttpServletRequest req) {
        return Response.builder()
                .code(ex.getStatus().value())
                .status(ex.getStatus())
                .message(ex.getMessage())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(req.getRequestURI())
                .build();
    }
}
