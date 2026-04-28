package pl.javakurs.model.exception;

public class WrongDateOrderException extends BadRequestException {
    public WrongDateOrderException(String message) {
        super(message);
    }
}
