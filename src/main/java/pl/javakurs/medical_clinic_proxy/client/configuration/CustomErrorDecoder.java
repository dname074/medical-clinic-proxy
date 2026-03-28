package pl.javakurs.medical_clinic_proxy.client.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import pl.javakurs.medical_clinic_proxy.exception.badrequest.BadRequestException;
import pl.javakurs.medical_clinic_proxy.exception.conflict.ConflictException;
import pl.javakurs.medical_clinic_proxy.exception.notfound.NotFoundException;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        String message = extractMessage(response);
        return switch (response.status()) {
            case 400 -> new BadRequestException(message);
            case 404 -> new NotFoundException(message);
            case 409 -> new ConflictException(message);
            case 503 -> new RetryableException(response.status(),
                    message,
                    response.request().httpMethod(),
                    null,
                    100L,
                    response.request());
            default -> new RuntimeException(message);
        };
    }

    private String extractMessage(Response response) {
        try (InputStream bodyIs = response.body().asInputStream()) {
            String body = new String(bodyIs.readAllBytes(), StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(body);
            return json.has("message") ? json.get("message").asText() : body;
        } catch (Exception e) {
            return "Unknown error";
        }
    }
}
