package pl.javakurs.medical_clinic_proxy.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import pl.javakurs.medical_clinic_proxy.dto.ExceptionDto;
import pl.javakurs.medical_clinic_proxy.dto.ValidationExceptionDto;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MedicalClinicException.class)
    public ResponseEntity<ExceptionDto> handleMedicalClinicException(MedicalClinicException exception) {
        exceptionLog(exception.getMessage());
        return ResponseEntity.status(exception.getStatus()).body(new ExceptionDto(exception.getMessage(), exception.getStatus()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionDto> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        exceptionLog(exception.getMessage());
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).body(new ExceptionDto(exception.getMessage(), httpStatus));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDto> handleValidationException(MethodArgumentNotValidException exception) {
        exceptionLog(exception.getMessage());
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        List<String> messages = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach(error -> messages.add(((FieldError) error).getField() + " - " + error.getDefaultMessage()));
        return ResponseEntity.status(httpStatus).body(new ValidationExceptionDto(httpStatus, messages));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionDto> handleMissingParameterException(MissingServletRequestParameterException exception) {
        exceptionLog(exception.getMessage());
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).body(new ExceptionDto("Missing parameter", httpStatus));
    }

    private void exceptionLog(String message) {
        log.error("Exception log: {}", message);
    }
}
