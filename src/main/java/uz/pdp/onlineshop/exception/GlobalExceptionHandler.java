package uz.pdp.onlineshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<?> userNotFound(UserNotFoundException exception){
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(exception.getMessage());
//    }
//    @ExceptionHandler(TrainNotFoundException.class)
//    public ResponseEntity<?> trainNotFound(TrainNotFoundException exception){
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(exception.getMessage());
//    }
//    @ExceptionHandler(TrainNotFoundExceptionForUserData.class)
//    public ResponseEntity<?> trainNotFoundForUser(TrainNotFoundExceptionForUserData exception){
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(exception.getMessage());
//    }
    @ExceptionHandler(EmailOrPasswordIncorrectException.class)
    public ResponseEntity<?> incorrectPassword(EmailOrPasswordIncorrectException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
    @ExceptionHandler(EmailAlreadyRegisteredExceptions.class)
    public ResponseEntity<?> emailAlreadyExists(EmailAlreadyRegisteredExceptions exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}
