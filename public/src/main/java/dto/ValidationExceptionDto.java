package dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ValidationExceptionDto(HttpStatus status, List<String> messages) {
}
