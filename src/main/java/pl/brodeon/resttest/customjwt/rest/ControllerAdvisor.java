package pl.brodeon.resttest.customjwt.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.brodeon.resttest.customjwt.rest.model.response.ErrorResponse;

import java.time.Instant;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

//@ControllerAdvice
//public class ControllerAdvisor extends ResponseEntityExceptionHandler {
//
//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
//        var status = HttpStatus.UNAUTHORIZED;
//        var response = ErrorResponse.builder()
//                .message(ex.getMessage())
//                .status(status.value())
//                .timestamp(Instant.now())
//                .build();
//        return new ResponseEntity<>(response, status);
//    }
//
//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
//        var status = HttpStatus.UNAUTHORIZED;
//        var response = ErrorResponse.builder()
//                .message(ex.getMessage())
//                .status(status.value())
//                .timestamp(Instant.now())
//                .build();
//        return new ResponseEntity<>(response, status);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
//        var status = HttpStatus.UNAUTHORIZED;
//        var response = ErrorResponse.builder()
//                .message(ex.getMessage())
//                .status(status.value())
//                .timestamp(Instant.now())
//                .build();
//        return new ResponseEntity<>(response, status);
//    }
//}
