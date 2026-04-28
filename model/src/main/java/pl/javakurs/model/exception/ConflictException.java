package pl.javakurs.model.exception;

import pl.javakurs.model.HttpStatus;

public class ConflictException extends MedicalClinicException {
    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
