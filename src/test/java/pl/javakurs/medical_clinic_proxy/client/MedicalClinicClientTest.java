package pl.javakurs.medical_clinic_proxy.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import pl.javakurs.medical_clinic_proxy.dto.PageDto;
import pl.javakurs.medical_clinic_proxy.dto.VisitDto;
import pl.javakurs.medical_clinic_proxy.model.Specialization;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.patchRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static pl.javakurs.medical_clinic_proxy.dataFactory.VisitTestDataFactory.createVisit;

@SpringBootTest
@AutoConfigureWireMock(port = 8085)
public class MedicalClinicClientTest {
    @Autowired
    private WireMockServer medicalClinicClientServer;
    @Autowired
    private MedicalClinicClient client;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        medicalClinicClientServer.resetAll();
    }

    @Test
    void getPatientVisits_ResponseStatus200_VisitsPageReturned() throws JsonProcessingException {
        Long patientId = 1L;
        int page = 0;
        int size = 1;
        List<VisitDto> visits = List.of(createVisit());
        PageDto<VisitDto> visitPage = new PageDto<>(visits, 1, page, size);
        medicalClinicClientServer.stubFor(WireMock.get("/visits/patients/1?page=0&size=1")
                .willReturn(
                        aResponse()
                                .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBody(objectMapper.writeValueAsString(visitPage))
                                .withStatus(200)
                ));
        PageDto<VisitDto> result = client.getPatientVisits(patientId, page, size);

        Assertions.assertAll(
                () -> assertEquals(visitPage.content(), result.content()),
                () -> assertEquals(1, result.totalPages()),
                () -> assertEquals(0, result.pageNumber()),
                () -> assertEquals(1, result.pageSize())
        );
        verify(1, getRequestedFor(urlPathEqualTo("/visits/patients/1"))
                .withQueryParam("page", equalTo("0"))
                .withQueryParam("size", equalTo("1")));
    }

    @Test
    void getFreeDoctorVisits_ResponseStatus200_VisitsPageReturned() throws JsonProcessingException {
        Long doctorId = 1L;
        int page = 0;
        int size = 1;
        List<VisitDto> visits = List.of(createVisit());
        PageDto<VisitDto> visitPage = new PageDto<>(visits, 1, page, size);
        medicalClinicClientServer.stubFor(WireMock.get("/visits/doctors/1?page=0&size=1")
                .willReturn(
                        aResponse()
                                .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBody(objectMapper.writeValueAsString(visitPage))
                                .withStatus(200)
                ));
        PageDto<VisitDto> result = client.getFreeDoctorVisits(doctorId, page, size);

        Assertions.assertAll(
                () -> assertEquals(visitPage.content(), result.content()),
                () -> assertEquals(1, result.totalPages()),
                () -> assertEquals(0, result.pageNumber()),
                () -> assertEquals(1, result.pageSize())
        );
        verify(1, getRequestedFor(urlPathEqualTo("/visits/doctors/1"))
                .withQueryParam("page", equalTo("0"))
                .withQueryParam("size", equalTo("1")));
    }

    @Test
    void getVisitsByDateAndDoctorSpecialization_ResponseStatus200_VisitsPageReturned() throws JsonProcessingException {
        Specialization specialization = Specialization.DERMATOLOGIST;
        LocalDate date = LocalDate.of(2027, 1, 1);
        int page = 0;
        int size = 1;
        List<VisitDto> visits = List.of(createVisit());
        PageDto<VisitDto> visitPage = new PageDto<>(visits, 1, page, size);
        medicalClinicClientServer.stubFor(WireMock.get("/visits/doctors?specialization=DERMATOLOGIST&date=2027-01-01&page=0&size=1")
                .willReturn(
                        aResponse()
                                .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBody(objectMapper.writeValueAsString(visitPage))
                                .withStatus(200)
                ));
        PageDto<VisitDto> result = client.getVisitsByDateAndDoctorSpecialization(specialization, date, page, size);

        Assertions.assertAll(
                () -> assertEquals(visitPage.content(), result.content()),
                () -> assertEquals(1, result.totalPages()),
                () -> assertEquals(0, result.pageNumber()),
                () -> assertEquals(1, result.pageSize())
        );
        verify(1, getRequestedFor(urlPathEqualTo("/visits/doctors"))
                .withQueryParam("specialization", equalTo("DERMATOLOGIST"))
                .withQueryParam("date", equalTo("2027-01-01"))
                .withQueryParam("page", equalTo("0"))
                .withQueryParam("size", equalTo("1")));
    }

    @Test
    void assignPatientToVisit_ResponseStatus200_VisitReturned() throws JsonProcessingException {
        Long visitId = 1L;
        Long patientId = 1L;
        VisitDto visit = createVisit();
        medicalClinicClientServer.stubFor(WireMock.patch(urlEqualTo("/visits/1/patients/1"))
                .willReturn(
                        aResponse()
                                .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBody(objectMapper.writeValueAsString(visit))
                                .withStatus(200)
                ));
        VisitDto result = client.assignPatientToVisit(visitId, patientId);

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
        verify(1, patchRequestedFor(urlEqualTo("/visits/1/patients/1")));
    }
}
