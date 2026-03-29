package pl.javakurs.medical_clinic_proxy.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.javakurs.medical_clinic_proxy.dto.PageDto;
import pl.javakurs.medical_clinic_proxy.dto.VisitDto;
import pl.javakurs.medical_clinic_proxy.model.Specialization;

import java.time.LocalDate;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.patchRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static pl.javakurs.medical_clinic_proxy.dataFactory.VisitTestDataFactory.createVisit;

@SpringBootTest
@AutoConfigureWireMock(port = 8085)
@AutoConfigureMockMvc
public class VisitControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    WireMockServer medicalClinicClient;
    @Autowired
    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        medicalClinicClient.resetAll();
    }

    @Test
    void getPatientVisits_CorrectDataPassed_VisitsPageReturned() throws Exception {
        int page = 0;
        int size = 1;
        List<VisitDto> visits = List.of(createVisit());
        PageDto<VisitDto> pageDto = new PageDto<>(visits, 1, 0, 1);
        medicalClinicClient.stubFor(WireMock.get("/visits/patients/1?page=0&size=1")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(mapper.writeValueAsString(pageDto))
                ));
        mockMvc.perform(MockMvcRequestBuilders.get("/visits/patients/1")
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
        verify(1, getRequestedFor(urlPathEqualTo("/visits/patients/1"))
                .withQueryParam("page", equalTo("0"))
                .withQueryParam("size", equalTo("1")));
    }

    @Test
    void getFreeDoctorVisits_CorrectDataPassed_VisitsPageReturned() throws Exception {
        int page = 0;
        int size = 1;
        List<VisitDto> visits = List.of(createVisit());
        PageDto<VisitDto> pageDto = new PageDto<>(visits, 1, 0, 1);
        medicalClinicClient.stubFor(WireMock.get("/visits/doctors/1?page=0&size=1")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(mapper.writeValueAsString(pageDto))
                ));
        mockMvc.perform(MockMvcRequestBuilders.get("/visits/doctors/1")
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
        verify(1, getRequestedFor(urlPathEqualTo("/visits/doctors/1"))
                .withQueryParam("page", equalTo("0"))
                .withQueryParam("size", equalTo("1")));
    }

    @Test
    void getVisitsByDateAndSpecialization_CorrectDataPassed_VisitsPageReturned() throws Exception {
        Specialization specialization = Specialization.DERMATOLOGIST;
        LocalDate date = LocalDate.of(2027, 1, 1);
        int page = 0;
        int size = 1;
        List<VisitDto> visits = List.of(createVisit());
        PageDto<VisitDto> pageDto = new PageDto<>(visits, 1, 0, 1);
        medicalClinicClient.stubFor(WireMock.get("/visits/doctors?specialization=DERMATOLOGIST&date=2027-01-01&page=0&size=1")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(mapper.writeValueAsString(pageDto))
                ));
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
        verify(1, getRequestedFor(urlPathEqualTo("/visits/doctors"))
                .withQueryParam("specialization", equalTo("DERMATOLOGIST"))
                .withQueryParam("date", equalTo("2027-01-01"))
                .withQueryParam("page", equalTo("0"))
                .withQueryParam("size", equalTo("1")));
    }

    @Test
    void assignPatientToVisit_CorrectDataPassed_VisitReturned() throws Exception {
        Long visitId = 1L;
        Long patientId = 1L;
        VisitDto visit = createVisit();

        medicalClinicClient.stubFor(WireMock.patch(urlEqualTo("/visits/1/patients/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(mapper.writeValueAsString(visit))
                ));
        mockMvc.perform(MockMvcRequestBuilders.patch("/visits/{visitId}/patients/{patientId}", visitId, patientId))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.startDate").value("2027-01-01T12:30:00"))
                .andExpect(jsonPath("$.endDate").value("2027-01-01T13:00:00"))
                .andExpect(jsonPath("$.doctor.user.firstName").value("Jan"))
                .andExpect(jsonPath("$.doctor.user.lastName").value("Kowalski"))
                .andExpect(jsonPath("$.patient.user.firstName").value("Piotr"))
                .andExpect(jsonPath("$.patient.user.lastName").value("Nowak"));
        verify(1, patchRequestedFor(urlEqualTo("/visits/1/patients/1")));
    }
}
