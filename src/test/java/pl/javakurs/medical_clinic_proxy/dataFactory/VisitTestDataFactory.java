package pl.javakurs.medical_clinic_proxy.dataFactory;

import pl.javakurs.medical_clinic_proxy.dto.PatientDto;
import pl.javakurs.medical_clinic_proxy.dto.UserDto;
import pl.javakurs.medical_clinic_proxy.dto.VisitDto;
import pl.javakurs.medical_clinic_proxy.dto.VisitForPatientDto;
import pl.javakurs.medical_clinic_proxy.model.VisitStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static pl.javakurs.medical_clinic_proxy.dataFactory.DoctorTestDataFactory.createDoctor;

public class VisitTestDataFactory {
    public static VisitDto createVisit() {
        return new VisitDto(
                LocalDateTime.of(2027, 1, 1, 12, 30, 0),
                LocalDateTime.of(2027, 1, 1, 13, 0, 0),
                VisitStatus.CURRENT,
                createDoctor(),
                new PatientDto("email@onet.pl", "001fn", "111999888",
                        LocalDate.of(2005, 1, 2),
                        new UserDto("Piotr", "Nowak")
                )
        );
    }

    public static VisitDto createFreeVisit() {
        return new VisitDto(
                LocalDateTime.of(2027, 1, 1, 12, 30, 0),
                LocalDateTime.of(2027, 1, 1, 13, 0, 0),
                VisitStatus.CURRENT,
                createDoctor(),
                null
        );
    }

    public static VisitDto createCanceledVisit() {
        return new VisitDto(
                LocalDateTime.of(2027, 1, 1, 12, 30, 0),
                LocalDateTime.of(2027, 1, 1, 13, 0, 0),
                VisitStatus.CANCELED,
                createDoctor(),
                null
        );
    }

    public static VisitForPatientDto createVisitForPatient() {
        return new VisitForPatientDto(
                LocalDateTime.of(2027, 1, 1, 12, 30, 0),
                LocalDateTime.of(2027, 1, 1, 13, 0, 0),
                VisitStatus.CURRENT,
                createDoctor()
        );
    }
}
