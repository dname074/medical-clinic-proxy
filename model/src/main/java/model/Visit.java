package model;

import java.time.LocalDateTime;

public record Visit(
        LocalDateTime startDate,
        LocalDateTime endDate,
        VisitStatus visitStatus,
        SimpleDoctor doctor,
        Patient patient
) {
}
