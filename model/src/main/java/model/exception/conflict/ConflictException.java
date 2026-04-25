package model.exception.conflict;

import model.HttpStatus;
import model.exception.MedicalClinicException;

public class ConflictException extends MedicalClinicException {
    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
