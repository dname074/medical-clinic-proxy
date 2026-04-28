package pl.javakurs.model;

import java.util.List;

public record ValidationException(HttpStatus status, List<String> messages) {
}
