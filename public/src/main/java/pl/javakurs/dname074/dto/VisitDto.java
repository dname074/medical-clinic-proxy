package pl.javakurs.dname074.dto;

import pl.javakurs.model.VisitStatus;

import java.time.LocalDateTime;

public record VisitDto(
        LocalDateTime startDate,
        LocalDateTime endDate,
        VisitStatus visitStatus,
        DoctorDto doctor,
        PatientDto patient
) {
}
