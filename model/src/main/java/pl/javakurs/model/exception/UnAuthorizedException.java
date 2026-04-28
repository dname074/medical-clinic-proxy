package pl.javakurs.model.exception;

import pl.javakurs.model.HttpStatus;

public class UnAuthorizedException extends MedicalClinicException {
    public UnAuthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
