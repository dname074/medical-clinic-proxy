package pl.javakurs.medical_clinic_proxy.exception.badrequest;

public class WrongDateOrderException extends BadRequestException {
    public WrongDateOrderException(String message) {
        super(message);
    }
}
