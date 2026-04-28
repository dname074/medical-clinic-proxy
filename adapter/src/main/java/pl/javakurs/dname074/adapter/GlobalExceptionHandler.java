package pl.javakurs.dname074.adapter;

import pl.javakurs.dname074.dto.ExceptionDto;
import pl.javakurs.dname074.dto.ValidationExceptionDto;
import lombok.extern.slf4j.Slf4j;
import pl.javakurs.model.exception.MedicalClinicException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(MedicalClinicException.class)
    public ResponseEntity<ExceptionDto> handleMedicalClinicException(MedicalClinicException exception) {
        exceptionLog(exception.getMessage(), exception.getClass().getName());
        HttpStatus status = HttpStatus.valueOf(exception.getStatus().toString());

        return ResponseEntity.status(status).body(new ExceptionDto(exception.getMessage(), status));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionDto> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        String message = String.format("Parameter: '%s' must be of type: '%s'",
                exception.getName(), exception.getRequiredType());
        exceptionLog(exception.getMessage(), exception.getClass().getName());
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).body(new ExceptionDto(message, httpStatus));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDto> handleValidationException(MethodArgumentNotValidException exception) {
        exceptionLog(exception.getMessage(), exception.getClass().getName());
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        List<String> messages = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach(error -> messages.add(((FieldError) error).getField() + " - " + error.getDefaultMessage()));
        return ResponseEntity.status(httpStatus).body(new ValidationExceptionDto(httpStatus, messages));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionDto> handleMissingParameterException(MissingServletRequestParameterException exception) {
        exceptionLog(exception.getMessage(), exception.getClass().getName());
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).body(new ExceptionDto("Missing parameter", httpStatus));
    }

    private void exceptionLog(String message, String exceptionName) {
        log.error("{} occured, model.Exception log: {}", exceptionName, message);
    }
}
