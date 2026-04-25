//package pl.javakurs.medical_clinic_proxy.controller;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import pl.javakurs.medical_clinic_proxy.dto.DoctorDto;
//import pl.javakurs.medical_clinic_proxy.dto.PageDto;
//import pl.javakurs.medical_clinic_proxy.model.Specialization;
//import pl.javakurs.medical_clinic_proxy.service.DoctorService;
//
//import java.util.List;
//
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static pl.javakurs.medical_clinic_proxy.dataFactory.DoctorTestDataFactory.createDoctor;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class DoctorControllerTest {
//    @MockitoBean
//    DoctorService service;
//    @Autowired
//    MockMvc mockMvc;
//
//    @Test
//    void getFilteredDoctors_DoctorFoundBySpecialization_DoctorsPageReturned() throws Exception {
//        Specialization specialization = Specialization.DERMATOLOGIST;
//        int page = 0;
//        int size = 1;
//
//        List<DoctorDto> doctors = List.of(createDoctor());
//        PageDto<DoctorDto> pageDto = new PageDto<>(doctors, 1, page, size);
//        when(service.getFilteredDoctors(page, size, specialization)).thenReturn(pageDto);
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
//        verify(service, times(1)).getFilteredDoctors(0, 1, specialization);
//        verifyNoMoreInteractions(service);
//    }
//}
