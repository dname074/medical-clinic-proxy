//package pl.javakurs.medical_clinic_proxy.client;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.tomakehurst.wiremock.WireMockServer;
//import com.github.tomakehurst.wiremock.client.WireMock;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
//import org.springframework.http.MediaType;
//import pl.javakurs.medical_clinic_proxy.dto.DoctorDto;
//import pl.javakurs.medical_clinic_proxy.dto.PageDto;
//import pl.javakurs.medical_clinic_proxy.dto.VisitDto;
//import pl.javakurs.medical_clinic_proxy.dto.VisitForPatientDto;
//import pl.javakurs.medical_clinic_proxy.model.Specialization;
//import pl.javakurs.medical_clinic_proxy.model.VisitStatus;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
//import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
//import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
//import static com.github.tomakehurst.wiremock.client.WireMock.patchRequestedFor;
//import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
//import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
//import static com.github.tomakehurst.wiremock.client.WireMock.verify;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
//import static pl.javakurs.medical_clinic_proxy.dataFactory.DoctorTestDataFactory.createDoctor;
//import static pl.javakurs.medical_clinic_proxy.dataFactory.VisitTestDataFactory.createCanceledVisit;
//import static pl.javakurs.medical_clinic_proxy.dataFactory.VisitTestDataFactory.createVisit;
//import static pl.javakurs.medical_clinic_proxy.dataFactory.VisitTestDataFactory.createVisitForPatient;
//
//@SpringBootTest
//@AutoConfigureWireMock(port = 8085)
//public class MedicalClinicClientTest {
//    @Autowired
//    private WireMockServer medicalClinicClientServer;
//    @Autowired
//    private MedicalClinicClient client;
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    void setup() {
//        medicalClinicClientServer.resetAll();
//    }
//
//    @Test
//    void getPatientVisits_ResponseStatus200_VisitsPageReturned() throws JsonProcessingException {
//        Long patientId = 1L;
//        int page = 0;
//        int size = 1;
//        List<VisitDto> visits = List.of(createVisit());
//        PageDto<VisitDto> visitPage = new PageDto<>(visits, 1, page, size);
//        medicalClinicClientServer.stubFor(WireMock.get("/visits/patients/1?page=0&size=1")
//                .willReturn(
//                        aResponse()
//                                .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                                .withBody(objectMapper.writeValueAsString(visitPage))
//                                .withStatus(200)
//                ));
//        PageDto<VisitDto> result = client.getPatientVisits(patientId, page, size);
//
//        Assertions.assertAll(
//                () -> assertEquals(visitPage.content(), result.content()),
//                () -> assertEquals(1, result.totalPages()),
//                () -> assertEquals(0, result.pageNumber()),
//                () -> assertEquals(1, result.pageSize())
//        );
//        verify(1, getRequestedFor(urlPathEqualTo("/visits/patients/1"))
//                .withQueryParam("page", equalTo("0"))
//                .withQueryParam("size", equalTo("1")));
//    }
//
//    @Test
//    void getDoctorVisits_ResponseStatus200_VisitsPageReturned() throws JsonProcessingException {
//        Long doctorId = 1L;
//        int page = 0;
//        int size = 1;
//        VisitAvailability availability = VisitAvailability.ALL;
//        List<VisitDto> visits = List.of(createVisit());
//        PageDto<VisitDto> visitPage = new PageDto<>(visits, 1, page, size);
//        medicalClinicClientServer.stubFor(WireMock.get("/visits/doctors/1?availability=ALL&page=0&size=1")
//                .willReturn(
//                        aResponse()
//                                .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                                .withBody(objectMapper.writeValueAsString(visitPage))
//                                .withStatus(200)
//                ));
//        PageDto<VisitDto> result = client.getDoctorVisits(doctorId, availability, page, size);
//
//        Assertions.assertAll(
//                () -> assertEquals(visitPage.content(), result.content()),
//                () -> assertEquals(1, result.totalPages()),
//                () -> assertEquals(0, result.pageNumber()),
//                () -> assertEquals(1, result.pageSize())
//        );
//        verify(1, getRequestedFor(urlPathEqualTo("/visits/doctors/1"))
//                .withQueryParam("availability", equalTo("ALL"))
//                .withQueryParam("page", equalTo("0"))
//                .withQueryParam("size", equalTo("1")));
//    }
//
//    @Test
//    void getFilteredVisits_ResponseStatus200_VisitsPageReturned() throws JsonProcessingException {
//        Specialization specialization = Specialization.DERMATOLOGIST;
//        LocalDate fromDate = LocalDate.of(2027, 1, 1);
//        LocalDate toDate = LocalDate.of(2027, 5, 1);
//        VisitAvailability availability = VisitAvailability.ALL;
//        int page = 0;
//        int size = 1;
//        List<VisitForPatientDto> visits = List.of(createVisitForPatient());
//        PageDto<VisitForPatientDto> visitPage = new PageDto<>(visits, 1, page, size);
//        medicalClinicClientServer.stubFor(WireMock.get("/visits/doctors?specialization=DERMATOLOGIST&from=2027-01-01&to=2027-05-01&availability=ALL&page=0&size=1")
//                .willReturn(
//                        aResponse()
//                                .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                                .withBody(objectMapper.writeValueAsString(visitPage))
//                                .withStatus(200)
//                ));
//        PageDto<VisitForPatientDto> result = client.getFilteredVisits(specialization, fromDate, toDate, availability, page, size);
//
//        Assertions.assertAll(
//                () -> assertEquals(visitPage.content(), result.content()),
//                () -> assertEquals(1, result.totalPages()),
//                () -> assertEquals(0, result.pageNumber()),
//                () -> assertEquals(1, result.pageSize())
//        );
//        verify(1, getRequestedFor(urlPathEqualTo("/visits/doctors"))
//                .withQueryParam("specialization", equalTo("DERMATOLOGIST"))
//                .withQueryParam("from", equalTo("2027-01-01"))
//                .withQueryParam("to", equalTo("2027-05-01"))
//                .withQueryParam("availability", equalTo("ALL"))
//                .withQueryParam("page", equalTo("0"))
//                .withQueryParam("size", equalTo("1")));
//    }
//
//    @Test
//    void getFilteredDoctors_ResponseStatus200_DoctorsPageReturned() throws JsonProcessingException {
//        int page = 0;
//        int size = 1;
//        Specialization specialization = Specialization.DERMATOLOGIST;
//        List<DoctorDto> doctors = List.of(createDoctor());
//        PageDto<DoctorDto> doctorPage = new PageDto<>(doctors, 1, page, size);
//        medicalClinicClientServer.stubFor(WireMock.get("/doctors?specialization=DERMATOLOGIST&page=0&size=1")
//                .willReturn(
//                        aResponse()
//                                .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                                .withBody(objectMapper.writeValueAsString(doctorPage))
//                                .withStatus(200)
//                ));
//
//        PageDto<DoctorDto> result = client.getFilteredDoctors(specialization, page, size);
//
//        Assertions.assertAll(
//                () -> assertEquals("Jan", result.content().getFirst().user().firstName()),
//                () -> assertEquals("Kowalski", result.content().getFirst().user().lastName()),
//                () -> assertEquals(Specialization.DERMATOLOGIST, result.content().getFirst().specialization())
//        );
//        verify(1, getRequestedFor(urlPathEqualTo("/doctors"))
//                .withQueryParam("specialization", equalTo("DERMATOLOGIST"))
//                .withQueryParam("page", equalTo("0"))
//                .withQueryParam("size", equalTo("1")));
//    }
//
//    @Test
//    void assignPatientToVisit_ResponseStatus200_VisitReturned() throws JsonProcessingException {
//        Long visitId = 1L;
//        Long patientId = 1L;
//        VisitDto visit = createVisit();
//        medicalClinicClientServer.stubFor(WireMock.patch(urlEqualTo("/visits/1/patients/1"))
//                .willReturn(
//                        aResponse()
//                                .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                                .withBody(objectMapper.writeValueAsString(visit))
//                                .withStatus(200)
//                ));
//        VisitDto result = client.assignPatientToVisit(visitId, patientId);
//
//        Assertions.assertAll(
//                () -> assertEquals(LocalDateTime.of(2027, 1, 1, 12, 30, 0), result.startDate()),
//                () -> assertEquals(LocalDateTime.of(2027, 1, 1, 13, 0, 0), result.endDate()),
//                () -> assertEquals("Jan", result.doctor().user().firstName()),
//                () -> assertEquals("Kowalski", result.doctor().user().lastName()),
//                () -> assertEquals("email@onet.pl", result.patient().email()),
//                () -> assertEquals("001fn", result.patient().idCardNo()),
//                () -> assertEquals("111999888", result.patient().phoneNumber()),
//                () -> assertEquals(LocalDate.of(2005, 1, 2), result.patient().birthday()),
//                () -> assertEquals("Piotr", result.patient().user().firstName()),
//                () -> assertEquals("Nowak", result.patient().user().lastName())
//        );
//        verify(1, patchRequestedFor(urlEqualTo("/visits/1/patients/1")));
//    }
//
//    @Test
//    void cancelVisit_ResponseStatus200_VisitReturned() throws JsonProcessingException {
//        Long id = 1L;
//        VisitDto visit = createCanceledVisit();
//        medicalClinicClientServer.stubFor(WireMock.patch(urlEqualTo("/visits/1"))
//                .willReturn(
//                        aResponse()
//                                .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                                .withBody(objectMapper.writeValueAsString(visit))
//                                .withStatus(200)
//                ));
//        VisitDto result = client.cancelVisit(id);
//
//        Assertions.assertAll(
//                () -> assertEquals(LocalDateTime.of(2027, 1, 1, 12, 30, 0), result.startDate()),
//                () -> assertEquals(LocalDateTime.of(2027, 1, 1, 13, 0, 0), result.endDate()),
//                () -> assertEquals("Jan", result.doctor().user().firstName()),
//                () -> assertEquals("Kowalski", result.doctor().user().lastName()),
//                () -> assertEquals(VisitStatus.CANCELED, result.visitStatus())
//        );
//        verify(1, patchRequestedFor(urlEqualTo("/visits/1")));
//    }
//}
