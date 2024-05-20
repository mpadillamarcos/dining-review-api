package mpadillamarcos.diningreview.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> notFound(NotFoundException exception) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(Map.of("message", exception.getMessage()));
    }

    @ExceptionHandler(UsernameNotAvailableException.class)
    public ResponseEntity<Map<String, String>> usernameNotAvailable(UsernameNotAvailableException exception) {
        return ResponseEntity
                .status(CONFLICT)
                .body(Map.of("message", exception.getMessage()));
    }

    @ExceptionHandler(RestaurantAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> restaurantAlreadyExists(RestaurantAlreadyExistsException exception) {
        return ResponseEntity
                .status(CONFLICT)
                .body(Map.of("message", exception.getMessage()));
    }

}
