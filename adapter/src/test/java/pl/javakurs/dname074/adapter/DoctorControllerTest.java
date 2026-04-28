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
import pl.javakurs.dname074.domain.DoctorServiceProvider;
import pl.javakurs.model.Doctor;
import pl.javakurs.model.Page;
import pl.javakurs.model.Specialization;

import java.util.List;

import static org.mockito.Mockito.*;
import static pl.javakurs.dname074.adapter.DoctorTestDataFactory.createDoctor;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class DoctorControllerTest {
    @MockitoBean
    DoctorServiceProvider service;
    @Autowired
    MockMvc mockMvc;
    DoctorMapper doctorMapper;

    @BeforeEach
    void setup() {
        this.doctorMapper = Mappers.getMapper(DoctorMapper.class);
    }

    @Test
    void getFilteredDoctors_DoctorFoundBySpecialization_DoctorsPageReturned() throws Exception {
        Specialization specialization = Specialization.DERMATOLOGIST;
        int page = 0;
        int size = 1;

        List<Doctor> doctors = List.of(createDoctor());
        Page<Doctor> doctorsPage = new Page<>(doctors, 1, page, size);
        when(service.getFilteredDoctors(page, size, specialization)).thenReturn(doctorsPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/doctors")
                        .param("specialization", String.valueOf(specialization))
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content[0].user.firstName").value("Jan"))
                .andExpect(jsonPath("$.content[0].user.lastName").value("Kowalski"))
                .andExpect(jsonPath("$.content[0].specialization").value("DERMATOLOGIST"))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.pageNumber").value(0))
                .andExpect(jsonPath("$.pageSize").value(1));
        verify(service, times(1)).getFilteredDoctors(0, 1, specialization);
        verifyNoMoreInteractions(service);
    }

    @Test
    void getFilteredDoctors_WrongDataFormatPassed_DoctorsPageReturned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/patient/doctors")
                .param("page", "s"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        verifyNoInteractions(service);
    }
}
