package model.exception.badrequest;

public class WrongDateOrderException extends BadRequestException {
    public WrongDateOrderException(String message) {
        super(message);
    }
}
