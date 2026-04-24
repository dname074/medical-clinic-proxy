package pl.javakurs.medical_clinic_proxy.exception;

import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends MedicalClinicException {
    public UnAuthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
