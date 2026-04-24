package pl.javakurs.medical_clinic_proxy.client.configuration;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import pl.javakurs.medical_clinic_proxy.exception.UnAuthorizedException;
import pl.javakurs.medical_clinic_proxy.exception.badrequest.BadRequestException;
import pl.javakurs.medical_clinic_proxy.exception.conflict.ConflictException;
import pl.javakurs.medical_clinic_proxy.exception.notfound.NotFoundException;

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
