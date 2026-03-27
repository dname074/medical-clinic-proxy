package pl.javakurs.medical_clinic_proxy.exception.notfound;

import org.springframework.http.HttpStatus;
import pl.javakurs.medical_clinic_proxy.exception.MedicalClinicException;

public class NotFoundException extends MedicalClinicException {
    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
