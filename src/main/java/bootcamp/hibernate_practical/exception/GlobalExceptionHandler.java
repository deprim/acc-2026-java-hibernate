package bootcamp.hibernate_practical.exception;

import bootcamp.hibernate_practical.dto.ApiErrorResponse;
import bootcamp.hibernate_practical.dto.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().
                forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(new ValidationErrorResponse("Validation failed", errors));
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleBookNotFoundException(BookNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorResponse(ex.getMessage()));

    }

    @ExceptionHandler(BookAlreadyBorrowedException.class)
    public ResponseEntity<ApiErrorResponse> handleBookAlreadyBorrowedException(BookAlreadyBorrowedException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiErrorResponse(ex.getMessage()));

    }

    @ExceptionHandler(BookIsNotBorrowedException.class)
    public ResponseEntity<ApiErrorResponse> handleBookIsNotBorrowedException(BookIsNotBorrowedException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiErrorResponse(ex.getMessage()));

    }

    @ExceptionHandler(BookNotAavailableException.class)
    public ResponseEntity<ApiErrorResponse> handleBookNotAvailableException(BookNotAavailableException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiErrorResponse(ex.getMessage()));

    }






}
