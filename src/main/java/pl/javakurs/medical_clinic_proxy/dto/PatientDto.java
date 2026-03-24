package pl.javakurs.medical_clinic_proxy.dto;

import java.time.LocalDate;

public record PatientDto(
        String email,
        String idCardNo,
        String phoneNumber,
        LocalDate birthday,
        String firstName,
        String lastName
        ) {
}
