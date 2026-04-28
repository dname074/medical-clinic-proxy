package pl.javakurs.dname074.dto;

import org.springframework.http.HttpStatus;

public record ExceptionDto(String message, HttpStatus status) {
}
