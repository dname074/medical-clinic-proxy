package pl.javakurs.model.exception;

import pl.javakurs.model.HttpStatus;

public class BadRequestException extends MedicalClinicException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
