package model.exception.badrequest;

import model.HttpStatus;
import model.exception.MedicalClinicException;

public class BadRequestException extends MedicalClinicException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
