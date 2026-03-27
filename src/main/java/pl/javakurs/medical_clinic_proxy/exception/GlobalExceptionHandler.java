package pl.javakurs.medical_clinic_proxy.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import pl.javakurs.medical_clinic_proxy.dto.ExceptionDto;

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

    private void exceptionLog(String message) {
        log.error("Exception log: {}", message);
    }
}
