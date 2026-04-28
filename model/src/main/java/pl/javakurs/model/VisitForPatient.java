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
public class VisitForPatient {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private VisitStatus visitStatus;
    private Doctor doctor;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        VisitForPatient patient = (VisitForPatient) o;
        return Objects.equals(startDate, patient.startDate) && Objects.equals(endDate, patient.endDate) && visitStatus == patient.visitStatus && Objects.equals(doctor, patient.doctor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, visitStatus, doctor);
    }

    @Override
    public String toString() {
        return "VisitForPatient{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", visitStatus=" + visitStatus +
                ", doctor=" + doctor +
                '}';
    }
}
