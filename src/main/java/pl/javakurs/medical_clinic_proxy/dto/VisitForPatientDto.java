package pl.javakurs.medical_clinic_proxy.dto;

import pl.javakurs.medical_clinic_proxy.model.VisitStatus;

import java.time.LocalDateTime;

public record VisitForPatientDto(
        LocalDateTime startDate,
        LocalDateTime endDate,
        VisitStatus visitStatus,
        DoctorDto doctor
) {
}
