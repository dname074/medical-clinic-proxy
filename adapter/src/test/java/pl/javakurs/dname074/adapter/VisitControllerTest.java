package pl.javakurs.dname074.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.javakurs.dname074.domain.VisitServiceProvider;
import pl.javakurs.model.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.javakurs.dname074.adapter.VisitTestDataFactory.createVisit;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static pl.javakurs.dname074.adapter.VisitTestDataFactory.createVisitForPatient;

@SpringBootTest
@AutoConfigureMockMvc
public class VisitControllerTest {
    @MockitoBean
    VisitServiceProvider service;
    @Autowired
    MockMvc mockMvc;
    VisitMapper visitMapper;

    @BeforeEach
    void setup() {
        this.visitMapper = Mappers.getMapper(VisitMapper.class);
    }

    @Test
    void getPatientVisits_PatientFound_PatientVisitsReturned() throws Exception {
        Long patientId = 1L;
        int pageNumber = 0;
        int pageSize = 1;
        List<Visit> visits = List.of(createVisit());
        Page<Visit> visitsPage = new Page<>(visits, 1, pageNumber, pageSize);
        when(service.getPatientVisits(patientId, pageNumber, pageSize)).thenReturn(visitsPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/{patientId}/visits", patientId)
                        .param("page", String.valueOf(pageNumber))
                        .param("size", String.valueOf(pageSize)))
                .andDo(print())
                .andExpect(jsonPath("$.content[0].startDate").value("2027-01-01T12:30:00"))
                .andExpect(jsonPath("$.content[0].endDate").value("2027-01-01T13:00:00"))
                .andExpect(jsonPath("$.content[0].doctor.user.firstName").value("Jan"))
                .andExpect(jsonPath("$.content[0].doctor.user.lastName").value("Kowalski"))
                .andExpect(jsonPath("$.content[0].patient.user.firstName").value("Piotr"))
                .andExpect(jsonPath("$.content[0].patient.user.lastName").value("Nowak"))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.pageNumber").value(0))
                .andExpect(jsonPath("$.pageSize").value(1));
        verify(service, times(1)).getPatientVisits(1L, 0, 1);
        verifyNoMoreInteractions(service);
    }

    @Test
    void getPatientVisits_WrongDataFormatPassed_400Returned() throws Exception {
        Long patientId = 1L;
        int pageNumber = 0;
        int pageSize = 1;
        List<Visit> visits = List.of(createVisit());
        Page<Visit> visitsPage = new Page<>(visits, 1, pageNumber, pageSize);
        when(service.getPatientVisits(patientId, pageNumber, pageSize)).thenReturn(visitsPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/{patientId}/visits", patientId)
                        .param("page", "s")
                        .param("size", String.valueOf(pageSize)))
                .andDo(print())
                .andExpect(status().isBadRequest());
        verifyNoInteractions(service);
    }

    @Test
    void getDoctorVisits_DoctorFound_DoctorVisitsReturned() throws Exception {
        Long doctorId = 1L;
        int page = 0;
        int size = 1;
        VisitStatus status = null;

        List<Visit> visits = List.of(createVisit());
        Page<Visit> visitsPage = new Page<>(visits, 1, page, size);
        when(service.getDoctorVisits(doctorId, status, page, size)).thenReturn(visitsPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/doctor/{doctorId}/visits", doctorId)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content[0].startDate").value("2027-01-01T12:30:00"))
                .andExpect(jsonPath("$.content[0].endDate").value("2027-01-01T13:00:00"))
                .andExpect(jsonPath("$.content[0].doctor.user.firstName").value("Jan"))
                .andExpect(jsonPath("$.content[0].doctor.user.lastName").value("Kowalski"))
                .andExpect(jsonPath("$.content[0].patient.user.firstName").value("Piotr"))
                .andExpect(jsonPath("$.content[0].patient.user.lastName").value("Nowak"))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.pageNumber").value(0))
                .andExpect(jsonPath("$.pageSize").value(1));
        verify(service, times(1)).getDoctorVisits(1L, null, 0, 1);
        verifyNoMoreInteractions(service);
    }

    @Test
    void getDoctorVisits_WrongDataFormatPassed_400Returned() throws Exception {
        Long doctorId = 1L;
        String page = "s";
        int size = 1;

        mockMvc.perform(MockMvcRequestBuilders.get("/doctor/{doctorId}/visits", doctorId)
                        .param("page", page)
                        .param("size", String.valueOf(size)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        verifyNoInteractions(service);
    }

    @Test
    void getFilteredVisits_VisitsFoundByDateRangeAndSpecialization_VisitsReturned() throws Exception {
        Specialization specialization = Specialization.DERMATOLOGIST;
        LocalDate fromDate = LocalDate.of(2027, 1, 1);
        LocalDate toDate = LocalDate.of(2027, 5, 1);
        LocalDate date = null;
        VisitStatus status = null;
        int page = 0;
        int size = 1;

        List<VisitForPatient> visits = List.of(createVisitForPatient());
        Page<VisitForPatient> visitsPage = new Page<>(visits, 1, 0, 1);
        when(service.getFilteredVisits(specialization, date, fromDate, toDate, status, page, size)).thenReturn(visitsPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/visits")
                        .param("specialization", String.valueOf(specialization))
                        .param("from", String.valueOf(fromDate))
                        .param("to", String.valueOf(toDate))
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content[0].startDate").value("2027-01-01T12:30:00"))
                .andExpect(jsonPath("$.content[0].endDate").value("2027-01-01T13:00:00"))
                .andExpect(jsonPath("$.content[0].doctor.user.firstName").value("Jan"))
                .andExpect(jsonPath("$.content[0].doctor.user.lastName").value("Kowalski"))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.pageNumber").value(0))
                .andExpect(jsonPath("$.pageSize").value(1));
        verify(service, times(1)).getFilteredVisits(specialization, date, fromDate, toDate, status, 0, 1);
        verifyNoMoreInteractions(service);
    }

    @Test
    void getFilteredVisits_VisitsFoundByExactDateSpecializationAndStatus_VisitsReturned() throws Exception {
        Specialization specialization = Specialization.DERMATOLOGIST;
        LocalDate fromDate = null;
        LocalDate toDate = null;
        LocalDate date = LocalDate.of(2027, 5, 1);
        VisitStatus status = VisitStatus.AVAILABLE;
        int page = 0;
        int size = 1;

        List<VisitForPatient> visits = List.of(createVisitForPatient());
        Page<VisitForPatient> visitsPage = new Page<>(visits, 1, 0, 1);
        when(service.getFilteredVisits(specialization, date, fromDate, toDate, status, page, size)).thenReturn(visitsPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/visits")
                        .param("specialization", String.valueOf(specialization))
                        .param("status", String.valueOf(status))
                        .param("date", String.valueOf(date))
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content[0].startDate").value("2027-01-01T12:30:00"))
                .andExpect(jsonPath("$.content[0].endDate").value("2027-01-01T13:00:00"))
                .andExpect(jsonPath("$.content[0].doctor.user.firstName").value("Jan"))
                .andExpect(jsonPath("$.content[0].doctor.user.lastName").value("Kowalski"))
                .andExpect(jsonPath("$.content[0].visitStatus").value("AVAILABLE"))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.pageNumber").value(0))
                .andExpect(jsonPath("$.pageSize").value(1));
        verify(service, times(1)).getFilteredVisits(specialization, date, fromDate, toDate, status, 0, 1);
        verifyNoMoreInteractions(service);
    }

    @Test
    void getFilteredVisits_wrongDateParamsPassed_400Returned() throws Exception {
        Specialization specialization = Specialization.DERMATOLOGIST;
        LocalDate fromDate = null;
        LocalDate toDate = LocalDate.of(2027, 5, 1);
        LocalDate date = null;
        VisitStatus status = VisitStatus.AVAILABLE;
        int page = 0;
        int size = 1;

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/visits")
                        .param("specialization", String.valueOf(specialization))
                        .param("status", String.valueOf(status))
                        .param("to", String.valueOf(toDate))
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        verifyNoInteractions(service);
    }

    @Test
    void getFilteredVisits_VisitsNotFound_EmptyPageReturned() throws Exception {
        Specialization specialization = Specialization.SURGEON;
        LocalDate fromDate = LocalDate.of(2027, 1, 1);
        LocalDate toDate = LocalDate.of(2027, 5, 1);
        LocalDate exactDate = null;
        VisitStatus status = null;
        int page = 0;
        int size = 1;

        Page<VisitForPatient> visitsPage = new Page<>(List.of(), 1, 0, 1);
        when(service.getFilteredVisits(specialization, exactDate, fromDate, toDate, status, page, size)).thenReturn(visitsPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/visits")
                        .param("specialization", String.valueOf(specialization))
                        .param("from", String.valueOf(fromDate))
                        .param("to", String.valueOf(toDate))
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.pageNumber").value(0))
                .andExpect(jsonPath("$.pageSize").value(1));
        verify(service, times(1)).getFilteredVisits(specialization, exactDate, fromDate, toDate, status, 0, 1);
        verifyNoMoreInteractions(service);
    }

    @Test
    void getFilteredVisits_WrongParametersFormatPassed_400Returned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/patient/visits")
                        .param("specialization", "DERM4TolfdIST")
                        .param("date", "2027-04.12")
                        .param("status", "AVAILABLE")
                        .param("page", "0")
                        .param("size", "1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(status().isBadRequest());
        verifyNoInteractions(service);
    }

    @Test
    void assignPatientToVisit_PatientAndVisitFound_VisitDtoReturned() throws Exception {
        Long patientId = 1L;
        Long visitId = 1L;

        Visit visit = createVisit();
        when(service.assignPatientToVisit(patientId, visitId)).thenReturn(visit);

        mockMvc.perform(MockMvcRequestBuilders.patch("/patient/{patientId}/visits/{visitId}", patientId, visitId))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.startDate").value("2027-01-01T12:30:00"))
                .andExpect(jsonPath("$.endDate").value("2027-01-01T13:00:00"))
                .andExpect(jsonPath("$.doctor.user.firstName").value("Jan"))
                .andExpect(jsonPath("$.doctor.user.lastName").value("Kowalski"))
                .andExpect(jsonPath("$.patient.user.firstName").value("Piotr"))
                .andExpect(jsonPath("$.patient.user.lastName").value("Nowak"));
        verify(service, times(1)).assignPatientToVisit(1L, 1L);
        verifyNoMoreInteractions(service);
    }

    @Test
    void assignPatientToVisit_WrongDataFormatPassed_400Returned() throws Exception {
        String patientId = "s";
        Long visitId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.patch("/patient/{patientId}/visits/{visitId}", patientId, visitId))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        verifyNoInteractions(service);
    }

    @Test
    void cancelVisit_VisitFound_VisitDtoReturned() throws Exception {
        Long id = 1L;

        Visit visit = createVisit();
        when(service.cancelVisit(id)).thenReturn(visit);

        mockMvc.perform(MockMvcRequestBuilders.patch("/doctor/visits/{visitId}", id))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.startDate").value("2027-01-01T12:30:00"))
                .andExpect(jsonPath("$.endDate").value("2027-01-01T13:00:00"))
                .andExpect(jsonPath("$.doctor.user.firstName").value("Jan"))
                .andExpect(jsonPath("$.doctor.user.lastName").value("Kowalski"))
                .andExpect(jsonPath("$.patient.user.firstName").value("Piotr"))
                .andExpect(jsonPath("$.patient.user.lastName").value("Nowak"));
        verify(service, times(1)).cancelVisit(1L);
        verifyNoMoreInteractions(service);
    }

    @Test
    void cancelVisit_WrongDataFormatPassed_400Returned() throws Exception {
        String id = "s";

        mockMvc.perform(MockMvcRequestBuilders.patch("/doctor/visits/{visitId}", id))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        verifyNoInteractions(service);
    }
}
