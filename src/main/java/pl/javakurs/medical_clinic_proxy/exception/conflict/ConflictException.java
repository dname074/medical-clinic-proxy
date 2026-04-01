package pl.javakurs.medical_clinic_proxy.exception.conflict;

import org.springframework.http.HttpStatus;
import pl.javakurs.medical_clinic_proxy.exception.MedicalClinicException;

public class ConflictException extends MedicalClinicException {
    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
