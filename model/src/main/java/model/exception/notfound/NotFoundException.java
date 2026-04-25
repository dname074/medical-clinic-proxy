package model.exception.notfound;

import model.HttpStatus;
import model.exception.MedicalClinicException;

public class NotFoundException extends MedicalClinicException {
    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
