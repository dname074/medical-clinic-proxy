package adapter.client.configuration;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import model.exception.unauthorized.UnAuthorizedException;
import model.exception.badrequest.BadRequestException;
import model.exception.conflict.ConflictException;
import model.exception.notfound.NotFoundException;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException exception = FeignException.errorStatus(methodKey, response);
        return switch (response.status()) {
            case 400 -> new BadRequestException(exception.getMessage());
            case 401 -> new UnAuthorizedException(exception.getMessage());
            case 404 -> new NotFoundException(exception.getMessage());
            case 409 -> new ConflictException(exception.getMessage());
            case 503 -> new RetryableException(response.status(),
                    exception.getMessage(),
                    response.request().httpMethod(),
                    exception,
                    100L,
                    response.request());
            default -> exception;
        };
    }
}
