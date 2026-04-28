package pl.javakurs.dname074.dto;

import pl.javakurs.model.VisitStatus;

import java.time.LocalDateTime;

public record VisitForPatientDto(
        LocalDateTime startDate,
        LocalDateTime endDate,
        VisitStatus visitStatus,
        DoctorDto doctor
) {
}
