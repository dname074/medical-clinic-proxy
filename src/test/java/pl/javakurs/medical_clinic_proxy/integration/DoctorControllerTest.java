//package pl.javakurs.medical_clinic_proxy.integration;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.tomakehurst.wiremock.WireMockServer;
//import com.github.tomakehurst.wiremock.client.WireMock;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import pl.javakurs.medical_clinic_proxy.dto.DoctorDto;
//import pl.javakurs.medical_clinic_proxy.dto.PageDto;
//import pl.javakurs.medical_clinic_proxy.model.Specialization;
//
//import java.util.List;
//
//import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
//import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
//import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
//import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
//import static com.github.tomakehurst.wiremock.client.WireMock.verify;
//import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static pl.javakurs.medical_clinic_proxy.dataFactory.DoctorTestDataFactory.createDoctor;
//
//@SpringBootTest
//@AutoConfigureWireMock(port = 8085)
//@AutoConfigureMockMvc
//public class DoctorControllerTest {
//    @Autowired
//    MockMvc mockMvc;
//    @Autowired
//    WireMockServer medicalClinicClient;
//    @Autowired
//    ObjectMapper mapper;
//
//    @BeforeEach
//    void setup() {
//        medicalClinicClient.resetAll();
//    }
//
//    @Test
//    void getFilteredDoctors_DoctorFoundBySpecialization_DoctorsPageReturned() throws Exception {
//        Specialization specialization = Specialization.DERMATOLOGIST;
//        int page = 0;
//        int size = 1;
//
//        List<DoctorDto> doctors = List.of(createDoctor());
//        PageDto<DoctorDto> pageDto = new PageDto<>(doctors, 1, page, size);
//        medicalClinicClient.stubFor(WireMock.get("/doctors?specialization=DERMATOLOGIST&page=0&size=1")
//                .willReturn(aResponse()
//                        .withStatus(200)
//                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                        .withBody(mapper.writeValueAsString(pageDto))
//                ));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/patient/doctors")
//                        .param("specialization", String.valueOf(specialization))
//                        .param("page", String.valueOf(page))
//                        .param("size", String.valueOf(size)))
//                .andDo(print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(jsonPath("$.content[0].user.firstName").value("Jan"))
//                .andExpect(jsonPath("$.content[0].user.lastName").value("Kowalski"))
//                .andExpect(jsonPath("$.content[0].specialization").value("DERMATOLOGIST"))
//                .andExpect(jsonPath("$.totalPages").value(1))
//                .andExpect(jsonPath("$.pageNumber").value(0))
//                .andExpect(jsonPath("$.pageSize").value(1));
//        verify(1, getRequestedFor(urlPathEqualTo("/doctors"))
//                .withQueryParam("specialization", equalTo("DERMATOLOGIST"))
//                .withQueryParam("page", equalTo("0"))
//                .withQueryParam("size", equalTo("1")));
//    }
//}
