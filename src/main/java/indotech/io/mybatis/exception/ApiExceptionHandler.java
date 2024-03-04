package indotech.io.mybatis.exception;

import indotech.io.mybatis.exception.custom.NotFoundException;
import indotech.io.mybatis.model.response.DefaultResponse;
import indotech.io.mybatis.model.response.ErrorsResponse;
import indotech.io.mybatis.model.response.ResponseMessage;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = DuplicateKeyException.class)
    public ResponseEntity<DefaultResponse> handleAlreadyExistsException(DuplicateKeyException e) {
        HttpStatus status = HttpStatus.CONFLICT;
        DefaultResponse response = new DefaultResponse(false, ResponseMessage.DATA_ALREADY_EXISTS);
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        DefaultResponse response = new DefaultResponse(false, ResponseMessage.DATA_NOT_FOUND);
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    @ExceptionHandler(value = EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        DefaultResponse response = new DefaultResponse(false, ResponseMessage.DATA_NOT_FOUND);
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ErrorsResponse response = new ErrorsResponse(false, ResponseMessage.DATA_INVALID, errors);
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleNotReadableExceptionErrors(HttpMessageNotReadableException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ResponseMessage.DATA_INVALID);

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ErrorsResponse response = new ErrorsResponse(false, ResponseMessage.DATA_INVALID, errors);
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchExceptionErrors(NoSuchElementException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        DefaultResponse response = new DefaultResponse(false, ResponseMessage.DATA_NOT_FOUND);
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        DefaultResponse response = new DefaultResponse(false, ResponseMessage.DATA_INVALID);
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }
}
