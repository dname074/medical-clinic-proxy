package pl.javakurs.dname074.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.javakurs.model.Doctor;
import pl.javakurs.model.Page;
import pl.javakurs.model.Specialization;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static pl.javakurs.dname074.domain.TestDataFactory.createDoctor;

public class DoctorServiceTest {
    private MedicalClinicProvider client;
    private DoctorServiceProvider service;

    @BeforeEach
    void setup() {
        this.client = Mockito.mock(MedicalClinicProvider.class);
        this.service = new DoctorServiceImpl(client);
    }

    @Test
    void getFilteredDoctors_DoctorFoundBySpecialization_DoctorsPageReturned() {
        Specialization specialization = Specialization.DERMATOLOGIST;
        int page = 0;
        int size = 1;
        List<Doctor> doctors = List.of(createDoctor());
        Page<Doctor> doctorsPage = new Page<>(doctors, 1, 0, 1);
        when(client.getFilteredDoctors(page, size, specialization)).thenReturn(doctorsPage);

        Page<Doctor> result = service.getFilteredDoctors(page, size, specialization);

        Assertions.assertAll(
                () -> assertEquals(doctors, result.getContent()),
                () -> assertEquals(0, result.getPageNumber()),
                () -> assertEquals(1, result.getPageSize()),
                () -> assertEquals(1, result.getTotalPages())
        );
        verify(client, times(1)).getFilteredDoctors(0, 1, specialization);
        verifyNoMoreInteractions(client);
    }
}
