package com.od.library_management_system.exception;

import com.od.library_management_system.enums.ResponseCode;
import com.od.library_management_system.utils.Response;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Response> dataNotFound(NoSuchElementException exception) {
        return errorMessage(exception, ResponseCode.ERR_LIB_0404, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response> badRequestException(IllegalArgumentException exception) {
        return errorMessage(exception, ResponseCode.ERR_LIB_0400, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response> handleMissingRequestBody(HttpMessageNotReadableException exception) {
        return errorMessage(exception, ResponseCode.ERR_LIB_0400, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Response> handleNoHandlerFoundException(NoHandlerFoundException exception) {
        return errorMessage(exception, ResponseCode.ERR_LIB_0400, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> invalidRequestException(MethodArgumentNotValidException exception) {

        Map<String, String> errorMap = exception.getBindingResult().getFieldErrors().stream()
                .filter(fieldError -> !ObjectUtils.isEmpty(fieldError.getDefaultMessage()))
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existingValue, newValue) -> newValue
                ));

        return errorMessage(new Exception(errorMap.toString()), ResponseCode.ERR_LIB_0400, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class, ValidationException.class})
    public ResponseEntity<Response> handleValidationExceptions(Exception exception) {
        return errorMessage(exception, ResponseCode.ERR_LIB_0400, HttpStatus.BAD_REQUEST);
    }

    private static String getDetailOfError(String description) {
        int detailIndex = description.indexOf("Detail: ");
        return (detailIndex != -1) ? description.substring(detailIndex + "Detail: ".length()) : description;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Response> handleDataAccessException(DataAccessException exception) {
        return errorMessage(exception, ResponseCode.ERR_LIB_0500, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> internalServerError(Exception exception) {
        return errorMessage(exception, ResponseCode.ERR_LIB_0500, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Response> dataIntegrityViolationException(DataIntegrityViolationException exception) {
        String description = getDetailOfError(exception.getMostSpecificCause().getMessage());
        ErrorDto error = ErrorDto.builder().code(ResponseCode.ERR_LIB_0400.getStatus()).message(description).description(description).build();
        return new ResponseEntity<>(Response.builder().status(ResponseCode.ERR_LIB_0400.getStatus()).message(ResponseCode.ERR_LIB_0400.getMessage())
                .errors(Collections.singletonList(error)).build(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Response> errorMessage(Exception exception, ResponseCode message, HttpStatus status) {
        String description = StringUtils.isEmpty(exception.getMessage()) ? message.getMessage() : exception.getMessage();
        ErrorDto error = ErrorDto.builder().code(message.getStatus()).message(description).description(description).build();
        return new ResponseEntity<>(Response.builder().status(message.getStatus()).message(message.getMessage())
                .errors(Collections.singletonList(error)).build(), status);
    }


}
