package model;

import java.time.LocalDateTime;

public record VisitForPatient(
        LocalDateTime startDate,
        LocalDateTime endDate,
        VisitStatus visitStatus,
        SimpleDoctor doctor
) {
}
