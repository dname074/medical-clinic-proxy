package pl.javakurs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Visit {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private VisitStatus visitStatus;
    private Doctor doctor;
    private Patient patient;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Visit visit = (Visit) o;
        return Objects.equals(startDate, visit.startDate) && Objects.equals(endDate, visit.endDate) && visitStatus == visit.visitStatus && Objects.equals(doctor, visit.doctor) && Objects.equals(patient, visit.patient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, visitStatus, doctor, patient);
    }

    @Override
    public String toString() {
        return "Visit{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", visitStatus=" + visitStatus +
                ", doctor=" + doctor +
                ", patient=" + patient +
                '}';
    }
}
