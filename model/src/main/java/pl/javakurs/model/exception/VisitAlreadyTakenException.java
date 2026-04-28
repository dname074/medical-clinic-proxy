package pl.javakurs.model.exception;

public class VisitAlreadyTakenException extends ConflictException {
    public VisitAlreadyTakenException(String message) {
        super(message);
    }
}
