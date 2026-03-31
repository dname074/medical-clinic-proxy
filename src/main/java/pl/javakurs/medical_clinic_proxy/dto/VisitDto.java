package pl.javakurs.medical_clinic_proxy.dto;

import java.time.LocalDateTime;

public record VisitDto(
        LocalDateTime startDate,
        LocalDateTime endDate,
        DoctorDto doctor,
        PatientDto patient
) {
}
