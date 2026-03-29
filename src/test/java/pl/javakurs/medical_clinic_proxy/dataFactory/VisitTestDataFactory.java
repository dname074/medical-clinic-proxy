package pl.javakurs.medical_clinic_proxy.dataFactory;

import pl.javakurs.medical_clinic_proxy.dto.DoctorDto;
import pl.javakurs.medical_clinic_proxy.dto.PatientDto;
import pl.javakurs.medical_clinic_proxy.dto.UserDto;
import pl.javakurs.medical_clinic_proxy.dto.VisitDto;
import pl.javakurs.medical_clinic_proxy.model.Specialization;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VisitTestDataFactory {
    public static VisitDto createVisit() {
        return new VisitDto(
                LocalDateTime.of(2027, 1, 1, 12, 30, 0),
                LocalDateTime.of(2027, 1, 1, 13, 0, 0),
                new DoctorDto(new UserDto("Jan", "Kowalski"), Specialization.DERMATOLOGIST),
                new PatientDto("email@onet.pl", "001fn", "111999888",
                        LocalDate.of(2005, 1, 2),
                        new UserDto("Piotr", "Nowak")
                )
        );
    }
}
