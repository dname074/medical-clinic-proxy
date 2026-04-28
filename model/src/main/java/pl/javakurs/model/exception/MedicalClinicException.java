package pl.javakurs.model.exception;

import lombok.Getter;
import pl.javakurs.model.HttpStatus;

@Getter
public class MedicalClinicException extends RuntimeException {
    private final HttpStatus status;

    public MedicalClinicException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
