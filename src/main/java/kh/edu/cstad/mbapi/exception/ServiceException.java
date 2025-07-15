package kh.edu.cstad.mbapi.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ServiceException {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleServiceException(
            ResponseStatusException e, HttpServletRequest request

    ){
          ErrorResponse<?> error =ErrorResponse.builder()
                .message("Service Business Logic Error")
                .code(e.getStatusCode().value())
                .timestamp(LocalDateTime.now())
                .detail(e.getReason())
                .build();

        return ResponseEntity.status(e.getStatusCode().value())
                .body(error);
    }
}