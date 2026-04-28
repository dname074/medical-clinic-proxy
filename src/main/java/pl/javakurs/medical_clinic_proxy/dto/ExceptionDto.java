package pl.javakurs.medical_clinic_proxy.dto;

import org.springframework.http.HttpStatus;

public record ExceptionDto(String message, HttpStatus status) {
}
