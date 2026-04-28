package pl.javakurs.dname074.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.javakurs.model.*;
import pl.javakurs.model.exception.BeforeCurrentDateException;

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
import static pl.javakurs.dname074.domain.TestDataFactory.*;

public class VisitServiceTest {
    private MedicalClinicProvider client;
    private VisitServiceProvider service;
    private final Clock clock = Clock.fixed(
            LocalDateTime.of(2026, 2, 15, 12, 0, 0)
                    .atZone(ZoneId.systemDefault())
                    .toInstant(),
            ZoneId.systemDefault()
    );

    @BeforeEach
    void setup() {
        this.client = Mockito.mock(MedicalClinicProvider.class);
        this.service = new VisitServiceImpl(client, clock);
    }

    @Test
    void getPatientVisits_PatientFound_VisitsPageReturned() {
        Long patientId = 1L;
        int page = 0;
        int size = 1;
        List<Visit> visits = List.of(createVisit());
        Page<Visit> visitsPage = new Page<>(visits, 1, 0, 1);

        when(client.getPatientVisits(patientId, page, size)).thenReturn(visitsPage);

        Page<Visit> result = service.getPatientVisits(patientId, page, size);

        Assertions.assertAll(
                () -> assertEquals(visits, result.getContent()),
                () -> assertEquals(0, result.getPageNumber()),
                () -> assertEquals(1, result.getPageSize()),
                () -> assertEquals(1, result.getTotalPages())
        );
        verify(client, times(1)).getPatientVisits(patientId, 0, 1);
        verifyNoMoreInteractions(client);
    }

    @Test
    void getFreeDoctorVisits_DoctorFound_VisitsReturned() {
        VisitStatus status = VisitStatus.AVAILABLE;
        Long doctorId = 1L;
        int page = 0;
        int size = 1;
        List<Visit> visits = List.of(createVisit());
        Page<Visit> visitsPage = new Page<>(visits, 1, 0, 1);

        when(client.getDoctorVisits(doctorId, status, page, size)).thenReturn(visitsPage);

        Page<Visit> result = service.getDoctorVisits(doctorId, status, page, size);

        Assertions.assertAll(
                () -> assertEquals(visits, result.getContent()),
                () -> assertEquals(0, result.getPageNumber()),
                () -> assertEquals(1, result.getPageSize()),
                () -> assertEquals(1, result.getTotalPages())
        );
        verify(client, times(1)).getDoctorVisits(1L, VisitStatus.AVAILABLE, 0, 1);
        verifyNoMoreInteractions(client);
    }

    @Test
    void getFilteredVisits_DoctorFoundAndFreeVisitFoundByDateAndSpecialization_VisitsReturned() {
        Specialization specialization = Specialization.DERMATOLOGIST;
        VisitStatus status = VisitStatus.AVAILABLE;
        LocalDate exactDate = null;
        LocalDate fromDate = LocalDate.of(2027, 1, 1);
        LocalDate toDate = LocalDate.of(2027, 5, 1);
        int page = 0;
        int size = 1;
        List<VisitForPatient> visits = List.of(createVisitForPatient());
        Page<VisitForPatient> visitsPage = new Page<>(visits, 1, 0, 1);
        when(client.getFilteredVisits(specialization, fromDate, toDate, status, page, size)).thenReturn(visitsPage);

        Page<VisitForPatient> result = service.getFilteredVisits(specialization, exactDate, fromDate, toDate, status, page, size);

        Assertions.assertAll(
                () -> assertEquals(visits, result.getContent()),
                () -> assertEquals(0, result.getPageNumber()),
                () -> assertEquals(1, result.getPageSize()),
                () -> assertEquals(1, result.getTotalPages())
        );
        verify(client, times(1)).getFilteredVisits(specialization, fromDate, toDate, VisitStatus.AVAILABLE, 0, 1);
        verifyNoMoreInteractions(client);
    }

    @Test
    void getFilteredVisits_OldDatePassed_BeforeCurrentDateExceptionThrown() {
        Specialization specialization = Specialization.DERMATOLOGIST;
        LocalDate date = LocalDate.of(2026, 1, 1);
        VisitStatus status = null;
        int page = 0;
        int size = 1;

        BeforeCurrentDateException exception = assertThrows(BeforeCurrentDateException.class,
                () -> service.getFilteredVisits(specialization, date, null, null, status, page, size));
        assertEquals("Past visits are no longer available" ,exception.getMessage());
        verifyNoInteractions(client);
    }

    @Test
    void assignPatientToVisit_PatientAndVisitFound_VisitReturned() {
        Long visitId =1L;
        Long patientId = 1L;
        Visit visit = createVisit();

        when(client.assignPatientToVisit(visitId, patientId)).thenReturn(visit);

        Visit result = service.assignPatientToVisit(visitId, patientId);
        Assertions.assertAll(
                () -> assertEquals(LocalDateTime.of(2027, 1, 1, 12, 30, 0), result.getStartDate()),
                () -> assertEquals(LocalDateTime.of(2027, 1, 1, 13, 0, 0), result.getEndDate()),
                () -> assertEquals("Jan", result.getDoctor().getUser().getFirstName()),
                () -> assertEquals("Kowalski", result.getDoctor().getUser().getLastName()),
                () -> assertEquals("email@onet.pl", result.getPatient().getEmail()),
                () -> assertEquals("001fn", result.getPatient().getIdCardNo()),
                () -> assertEquals("111999888", result.getPatient().getPhoneNumber()),
                () -> assertEquals(LocalDate.of(2005, 1, 2), result.getPatient().getBirthday()),
                () -> assertEquals("Piotr", result.getPatient().getUser().getFirstName()),
                () -> assertEquals("Nowak", result.getPatient().getUser().getLastName())
        );
        verify(client, times(1)).assignPatientToVisit(1L, 1L);
        verifyNoMoreInteractions(client);
    }

    @Test
    void cancelVisit_VisitFound_VisitReturned() {
        Long id = 1L;
        Visit visit = createCanceledVisit();
        when(client.cancelVisit(id)).thenReturn(visit);

        Visit result = service.cancelVisit(id);
        Assertions.assertAll(
                () -> assertEquals(LocalDateTime.of(2027, 1, 1, 12, 30, 0), result.getStartDate()),
                () -> assertEquals(LocalDateTime.of(2027, 1, 1, 13, 0, 0), result.getEndDate()),
                () -> assertEquals("Jan", result.getDoctor().getUser().getFirstName()),
                () -> assertEquals("Kowalski", result.getDoctor().getUser().getLastName()),
                () -> assertEquals(VisitStatus.CANCELED, result.getVisitStatus())
        );
        verify(client, times(1)).cancelVisit(1L);
        verifyNoMoreInteractions(client);
    }
}
