package dto;

import model.VisitStatus;

import java.time.LocalDateTime;

public record VisitDto(
        LocalDateTime startDate,
        LocalDateTime endDate,
        VisitStatus visitStatus,
        DoctorDto doctor,
        PatientDto patient
) {
}
