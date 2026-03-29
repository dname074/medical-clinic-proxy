package pl.javakurs.medical_clinic_proxy.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.javakurs.medical_clinic_proxy.client.MedicalClinicClient;
import pl.javakurs.medical_clinic_proxy.dto.PageDto;
import pl.javakurs.medical_clinic_proxy.dto.VisitDto;
import pl.javakurs.medical_clinic_proxy.exception.badrequest.BeforeCurrentDateException;
import pl.javakurs.medical_clinic_proxy.model.Specialization;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static pl.javakurs.medical_clinic_proxy.dataFactory.VisitTestDataFactory.createVisit;

@ExtendWith(MockitoExtension.class)
public class VisitServiceTest {
    @Mock
    private MedicalClinicClient client;
    private final Clock clock = Clock.fixed(
            LocalDateTime.of(2026, 2, 15, 12, 0, 0)
                    .atZone(ZoneId.systemDefault())
                    .toInstant(),
            ZoneId.systemDefault()
    );
    private VisitService service;

    @BeforeEach
    void setup() {
        this.service = new VisitService(client, clock);
    }

    @Test
    void getPatientVisits_PatientFound_VisitsPageReturned() {
        Long patientId = 1L;
        int page = 0;
        int size = 1;
        List<VisitDto> visits = List.of(createVisit());
        PageDto<VisitDto> visitsPage = new PageDto<>(visits, 1, 0, 1);

        when(client.getPatientVisits(patientId, page, size)).thenReturn(visitsPage);

        PageDto<VisitDto> result = service.getPatientVisits(patientId, page, size);

        Assertions.assertAll(
                () -> assertEquals(visits, result.content()),
                () -> assertEquals(0, result.pageNumber()),
                () -> assertEquals(1, result.pageSize()),
                () -> assertEquals(1, result.totalPages())
        );
        verify(client, times(1)).getPatientVisits(patientId, 0, 1);
        verifyNoMoreInteractions(client);
    }

    @Test
    void getFreeDoctorVisits_DoctorFound_VisitsReturned() {
        Long doctorId = 1L;
        int page = 0;
        int size = 1;
        List<VisitDto> visits = List.of(createVisit());
        PageDto<VisitDto> visitsPage = new PageDto<>(visits, 1, 0, 1);

        when(client.getFreeDoctorVisits(doctorId, page, size)).thenReturn(visitsPage);

        PageDto<VisitDto> result = service.getFreeDoctorVisits(doctorId, page, size);

        Assertions.assertAll(
                () -> assertEquals(visits, result.content()),
                () -> assertEquals(0, result.pageNumber()),
                () -> assertEquals(1, result.pageSize()),
                () -> assertEquals(1, result.totalPages())
        );
        verify(client, times(1)).getFreeDoctorVisits(1L, 0, 1);
        verifyNoMoreInteractions(client);
    }

    @Test
    void getVisitsByDateAndSpecialization_DoctorFound_VisitsReturned() {
        Specialization specialization = Specialization.DERMATOLOGIST;
        LocalDate date = LocalDate.of(2027, 1, 1);
        int page = 0;
        int size = 1;
        List<VisitDto> visits = List.of(createVisit());
        PageDto<VisitDto> visitsPage = new PageDto<>(visits, 1, 0, 1);
        when(client.getVisitsByDateAndDoctorSpecialization(specialization, date, page, size)).thenReturn(visitsPage);

        PageDto<VisitDto> result = service.getVisitsByDateAndSpecialization(specialization, date, page, size);

        Assertions.assertAll(
                () -> assertEquals(visits, result.content()),
                () -> assertEquals(0, result.pageNumber()),
                () -> assertEquals(1, result.pageSize()),
                () -> assertEquals(1, result.totalPages())
        );
        verify(client, times(1)).getVisitsByDateAndDoctorSpecialization(specialization, date, 0, 1);
        verifyNoMoreInteractions(client);
    }

    @Test
    void getVisitsByDateAndSpecialization_OldDatePassed_ExceptionThrown() {
        Specialization specialization = Specialization.DERMATOLOGIST;
        LocalDate date = LocalDate.of(2026, 1, 1);
        int page = 0;
        int size = 1;

        BeforeCurrentDateException exception = assertThrows(BeforeCurrentDateException.class,
                () -> service.getVisitsByDateAndSpecialization(specialization, date, page, size));
        assertEquals("Past visits are no longer available" ,exception.getMessage());
        verifyNoInteractions(client);
    }

    @Test
    void assignPatientToVisit_PatientAndVisitFound_VisitReturned() {
        Long visitId =1L;
        Long patientId = 1L;
        VisitDto visit = createVisit();

        when(client.assignPatientToVisit(visitId, patientId)).thenReturn(visit);

        VisitDto result = service.assignPatientToVisit(visitId, patientId);
        Assertions.assertAll(
                () -> assertEquals(LocalDateTime.of(2027, 1, 1, 12, 30, 0), result.startDate()),
                () -> assertEquals(LocalDateTime.of(2027, 1, 1, 13, 0, 0), result.endDate()),
                () -> assertEquals("Jan", result.doctor().user().firstName()),
                () -> assertEquals("Kowalski", result.doctor().user().lastName()),
                () -> assertEquals("email@onet.pl", result.patient().email()),
                () -> assertEquals("001fn", result.patient().idCardNo()),
                () -> assertEquals("111999888", result.patient().phoneNumber()),
                () -> assertEquals(LocalDate.of(2005, 1, 2), result.patient().birthday()),
                () -> assertEquals("Piotr", result.patient().user().firstName()),
                () -> assertEquals("Nowak", result.patient().user().lastName())
        );
        verify(client, times(1)).assignPatientToVisit(1L, 1L);
        verifyNoMoreInteractions(client);
    }
}
