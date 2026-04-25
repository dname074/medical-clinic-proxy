package model.exception.conflict;

public class VisitAlreadyTakenException extends ConflictException {
    public VisitAlreadyTakenException(String message) {
        super(message);
    }
}
