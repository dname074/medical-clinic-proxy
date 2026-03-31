package pl.javakurs.medical_clinic_proxy.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.javakurs.medical_clinic_proxy.dto.PageDto;
import pl.javakurs.medical_clinic_proxy.dto.VisitDto;
import pl.javakurs.medical_clinic_proxy.dto.VisitForPatientDto;
import pl.javakurs.medical_clinic_proxy.model.Specialization;
import pl.javakurs.medical_clinic_proxy.model.VisitAvailability;
import pl.javakurs.medical_clinic_proxy.service.VisitService;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.javakurs.medical_clinic_proxy.dataFactory.VisitTestDataFactory.createVisit;
import static pl.javakurs.medical_clinic_proxy.dataFactory.VisitTestDataFactory.createVisitForPatient;

@SpringBootTest
@AutoConfigureMockMvc
public class VisitControllerTest {
    @MockitoBean
    VisitService service;
    @Autowired
    MockMvc mockMvc;

    @Test
    void getPatientVisits_PatientFound_PatientVisitsReturned() throws Exception {
        Long patientId = 1L;
        int page = 0;
        int size = 1;

        List<VisitDto> visits = List.of(createVisit());
        PageDto<VisitDto> pageDto = new PageDto<>(visits, 1, 0, 1);
        when(service.getPatientVisits(patientId, page, size)).thenReturn(pageDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/{patientId}/visits", patientId)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
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
    void getDoctorVisits_DoctorFound_DoctorVisitsReturned() throws Exception {
        Long doctorId = 1L;
        int page = 0;
        int size = 1;
        VisitAvailability availability = VisitAvailability.ALL;

        List<VisitDto> visits = List.of(createVisit());
        PageDto<VisitDto> pageDto = new PageDto<>(visits, 1, 0, 1);
        when(service.getDoctorVisits(doctorId, availability, page, size)).thenReturn(pageDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/doctor/{doctorId}/visits", doctorId)
                        .param("availability", String.valueOf(availability))
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
        verify(service, times(1)).getDoctorVisits(1L, VisitAvailability.ALL, 0, 1);
        verifyNoMoreInteractions(service);
    }

    @Test
    void getFilteredVisits_VisitsFoundByDateAndSpecialization_VisitsReturned() throws Exception {
        Specialization specialization = Specialization.DERMATOLOGIST;
        LocalDate fromDate = LocalDate.of(2027, 1, 1);
        LocalDate toDate = LocalDate.of(2027, 5, 1);
        VisitAvailability availability = VisitAvailability.ALL;
        int page = 0;
        int size = 1;

        List<VisitForPatientDto> visits = List.of(createVisitForPatient());
        PageDto<VisitForPatientDto> pageDto = new PageDto<>(visits, 1, 0, 1);
        when(service.getFilteredVisits(specialization, fromDate, toDate, availability, page, size)).thenReturn(pageDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/visits")
                        .param("specialization", String.valueOf(specialization))
                        .param("from", String.valueOf(fromDate))
                        .param("to", String.valueOf(toDate))
                        .param("availability", String.valueOf(availability))
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
        verify(service, times(1)).getFilteredVisits(specialization, fromDate, toDate, availability, 0, 1);
        verifyNoMoreInteractions(service);
    }

    @Test
    void getFilteredVisits_VisitsNotFound_VisitsReturned() throws Exception {
        Specialization specialization = Specialization.SURGEON;
        LocalDate fromDate = LocalDate.of(2027, 1, 1);
        LocalDate toDate = LocalDate.of(2027, 5, 1);
        VisitAvailability availability = VisitAvailability.ALL;
        int page = 0;
        int size = 1;

        PageDto<VisitForPatientDto> pageDto = new PageDto<>(null, 1, 0, 1);
        when(service.getFilteredVisits(specialization, fromDate, toDate, availability, page, size)).thenReturn(pageDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/visits")
                        .param("specialization", String.valueOf(specialization))
                        .param("from", String.valueOf(fromDate))
                        .param("to", String.valueOf(toDate))
                        .param("availability", String.valueOf(availability))
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content").doesNotExist())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.pageNumber").value(0))
                .andExpect(jsonPath("$.pageSize").value(1));
        verify(service, times(1)).getFilteredVisits(specialization, fromDate, toDate, availability, 0, 1);
        verifyNoMoreInteractions(service);
    }

    @Test
    void getFilteredVisits_WrongParametersFormatPassed_400Returned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/patient/visits")
                        .param("specialization", "DERM4TolfdIST")
                        .param("date", "2027-04.12")
                        .param("availability", "FREE")
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

        VisitDto visit = createVisit();
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
    void cancelVisit_VisitFound_VisitDtoReturned() throws Exception {
        Long id = 1L;

        VisitDto visit = createVisit();
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
}
