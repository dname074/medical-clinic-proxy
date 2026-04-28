package pl.javakurs.model.exception;

import pl.javakurs.model.HttpStatus;

public class NotFoundException extends MedicalClinicException {
    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
