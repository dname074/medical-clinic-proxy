package dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.Setter;
import model.Specialization;
import model.VisitStatus;

import java.time.LocalDate;

@Getter
@Setter
public class FilteredVisitsRequest {
    private Specialization specialization;
    @FutureOrPresent
    private LocalDate from;
    @FutureOrPresent
    private LocalDate to;
    @FutureOrPresent
    private LocalDate date;
    private VisitStatus status;
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
