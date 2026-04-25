//package pl.javakurs.medical_clinic_proxy.service;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import pl.javakurs.medical_clinic_proxy.client.MedicalClinicClient;
//import pl.javakurs.medical_clinic_proxy.dto.DoctorDto;
//import pl.javakurs.medical_clinic_proxy.dto.PageDto;
//import pl.javakurs.medical_clinic_proxy.model.Specialization;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//import static org.mockito.Mockito.when;
//import static pl.javakurs.medical_clinic_proxy.dataFactory.DoctorTestDataFactory.createDoctor;
//
//@ExtendWith(MockitoExtension.class)
//public class DoctorServiceTest {
//    @Mock
//    private MedicalClinicClient client;
//    private DoctorService service;
//
//    @BeforeEach
//    void setup() {
//        this.service = new DoctorService(client);
//    }
//
//    @Test
//    void getFilteredDoctors_DoctorFoundBySpecialization_DoctorsPageReturned() {
//        Specialization specialization = Specialization.DERMATOLOGIST;
//        int page = 0;
//        int size = 1;
//        List<DoctorDto> doctors = List.of(createDoctor());
//        PageDto<DoctorDto> doctorsPage = new PageDto<>(doctors, 1, 0, 1);
//        when(client.getFilteredDoctors(specialization, page, size)).thenReturn(doctorsPage);
//
//        PageDto<DoctorDto> result = service.getFilteredDoctors(page, size, specialization);
//
//        Assertions.assertAll(
//                () -> assertEquals(doctors, result.content()),
//                () -> assertEquals(0, result.pageNumber()),
//                () -> assertEquals(1, result.pageSize()),
//                () -> assertEquals(1, result.totalPages())
//        );
//        verify(client, times(1)).getFilteredDoctors(specialization, 0, 1);
//        verifyNoMoreInteractions(client);
//    }
//}
