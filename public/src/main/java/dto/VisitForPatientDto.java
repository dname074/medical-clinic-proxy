package dto;

import model.VisitStatus;

import java.time.LocalDateTime;

public record VisitForPatientDto(
        LocalDateTime startDate,
        LocalDateTime endDate,
        VisitStatus visitStatus,
        DoctorDto doctor
) {
}
