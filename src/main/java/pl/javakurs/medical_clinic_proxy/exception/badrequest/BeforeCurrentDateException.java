package pl.javakurs.medical_clinic_proxy.exception.badrequest;

public class BeforeCurrentDateException extends BadRequestException {
    public BeforeCurrentDateException(String message) {
        super(message);
    }
}
