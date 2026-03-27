package pl.javakurs.medical_clinic_proxy.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ValidationExceptionDto(HttpStatus status, List<String> messages) {
}
