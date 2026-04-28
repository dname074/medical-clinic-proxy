package pl.javakurs.model.exception;

public class BeforeCurrentDateException extends BadRequestException {
    public BeforeCurrentDateException(String message) {
        super(message);
    }
}
