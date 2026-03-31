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
import pl.javakurs.medical_clinic_proxy.model.Specialization;
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

        mockMvc.perform(MockMvcRequestBuilders.get("/visits/patients/{patientId}", patientId)
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
    void getFreeDoctorVisits_DoctorFound_DoctorVisitsReturned() throws Exception {
        Long doctorId = 1L;
        int page = 0;
        int size = 1;

        List<VisitDto> visits = List.of(createVisit());
        PageDto<VisitDto> pageDto = new PageDto<>(visits, 1, 0, 1);
        when(service.getFreeDoctorVisits(doctorId, page, size)).thenReturn(pageDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/visits/doctors/{doctorId}", doctorId)
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
        verify(service, times(1)).getFreeDoctorVisits(1L, 0, 1);
        verifyNoMoreInteractions(service);
    }

    @Test
    void getVisitsByDateAndSpecialization_VisitsFound_VisitsReturned() throws Exception {
        Specialization specialization = Specialization.DERMATOLOGIST;
        LocalDate date = LocalDate.of(2027, 1, 1);
        int page = 0;
        int size = 1;

        List<VisitDto> visits = List.of(createVisit());
        PageDto<VisitDto> pageDto = new PageDto<>(visits, 1, 0, 1);
        when(service.getVisitsByDateAndSpecialization(specialization, date, page, size)).thenReturn(pageDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/visits/doctors")
                        .param("specialization", String.valueOf(specialization))
                        .param("date", String.valueOf(date))
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
        verify(service, times(1)).getVisitsByDateAndSpecialization(specialization, date, 0, 1);
        verifyNoMoreInteractions(service);
    }

    @Test
    void getVisitsByDateAndSpecialization_VisitsNotFound_VisitsReturned() throws Exception {
        Specialization specialization = Specialization.DERMATOLOGIST;
        LocalDate date = LocalDate.of(2027, 1, 1);
        int page = 0;
        int size = 1;

        PageDto<VisitDto> pageDto = new PageDto<>(null, 1, 0, 1);
        when(service.getVisitsByDateAndSpecialization(specialization, date, page, size)).thenReturn(pageDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/visits/doctors")
                        .param("specialization", String.valueOf(specialization))
                        .param("date", String.valueOf(date))
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content").doesNotExist())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.pageNumber").value(0))
                .andExpect(jsonPath("$.pageSize").value(1));
        verify(service, times(1)).getVisitsByDateAndSpecialization(specialization, date, 0, 1);
        verifyNoMoreInteractions(service);
    }

    @Test
    void getVisitsByDateAndSpecialization_WrongParametersFormatPassed_400Returned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/visits/doctors")
                        .param("specialization", "DERM4TolfdIST")
                        .param("date", "2027-04.12")
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

        mockMvc.perform(MockMvcRequestBuilders.patch("/visits/{visitId}/patients/{patientId}", visitId, patientId))
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
}
