package pl.javakurs.medical_clinic_proxy.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import lombok.Getter;
import lombok.Setter;
import pl.javakurs.medical_clinic_proxy.model.Specialization;
import pl.javakurs.medical_clinic_proxy.model.VisitAvailability;

import java.time.LocalDate;

@Getter
@Setter
public class FilteredVisitsRequest {
    private Specialization specialization;
    @Future
    private LocalDate from;
    @Future
    private LocalDate to;
    @Future
    private LocalDate date;
    private VisitAvailability availability = VisitAvailability.FREE;
    private Integer page = 0;
    private Integer size = 10;

    @AssertTrue(message = "Give 'date' or 'from' and 'to'")
    public boolean isDateOrRangeValid() {
        boolean hasDate = date != null;
        boolean hasRange = from != null & to != null;
        return (hasDate && !hasRange) || (!hasDate && hasRange);
    }

    @AssertTrue(message = "'from' has to be before 'to'")
    public boolean isDateRangeValid() {
        if (from == null && to == null) return true;
        if (from == null || to == null) return false;
        return from.isBefore(to);
    }
}
