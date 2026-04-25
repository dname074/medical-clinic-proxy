package model.exception.badrequest;

public class BeforeCurrentDateException extends BadRequestException {
    public BeforeCurrentDateException(String message) {
        super(message);
    }
}
