package pl.javakurs.dname074.domain;

import pl.javakurs.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestDataFactory {
    public static Doctor createDoctor() {
        return new Doctor(new User("Jan", "Kowalski"), Specialization.DERMATOLOGIST);
    }

    public static Visit createVisit() {
        return new Visit(
                LocalDateTime.of(2027, 1, 1, 12, 30, 0),
                LocalDateTime.of(2027, 1, 1, 13, 0, 0),
                VisitStatus.BOOKED,
                createDoctor(),
                new Patient("email@onet.pl", "001fn", "111999888",
                        LocalDate.of(2005, 1, 2),
                        new User("Piotr", "Nowak")
                )
        );
    }

    public static Visit createFreeVisit() {
        return new Visit(
                LocalDateTime.of(2027, 1, 1, 12, 30, 0),
                LocalDateTime.of(2027, 1, 1, 13, 0, 0),
                VisitStatus.AVAILABLE,
                createDoctor(),
                null
        );
    }

    public static Visit createCanceledVisit() {
        return new Visit(
                LocalDateTime.of(2027, 1, 1, 12, 30, 0),
                LocalDateTime.of(2027, 1, 1, 13, 0, 0),
                VisitStatus.CANCELED,
                createDoctor(),
                null
        );
    }

    public static VisitForPatient createVisitForPatient() {
        return new VisitForPatient(
                LocalDateTime.of(2027, 1, 1, 12, 30, 0),
                LocalDateTime.of(2027, 1, 1, 13, 0, 0),
                VisitStatus.AVAILABLE,
                createDoctor()
        );
    }
}
