package model.exception.unauthorized;

import model.HttpStatus;
import model.exception.MedicalClinicException;

public class UnAuthorizedException extends MedicalClinicException {
    public UnAuthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
