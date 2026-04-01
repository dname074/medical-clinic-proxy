package pl.javakurs.medical_clinic_proxy.exception.conflict;

public class VisitAlreadyTakenException extends ConflictException {
    public VisitAlreadyTakenException(String message) {
        super(message);
    }
}
