package pl.javakurs.medical_clinic_proxy.exception.badrequest;

import org.springframework.http.HttpStatus;
import pl.javakurs.medical_clinic_proxy.exception.MedicalClinicException;

public class BadRequestException extends MedicalClinicException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
