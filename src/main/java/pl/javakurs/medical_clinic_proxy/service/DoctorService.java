package pl.javakurs.medical_clinic_proxy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.javakurs.medical_clinic_proxy.client.MedicalClinicClient;
import pl.javakurs.medical_clinic_proxy.dto.DoctorDto;
import pl.javakurs.medical_clinic_proxy.dto.PageDto;
import pl.javakurs.medical_clinic_proxy.model.Specialization;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoctorService {
    private final MedicalClinicClient client;

    public PageDto<DoctorDto> getFilteredDoctors(Integer page, Integer size, Specialization specialization) {
        log.info("Process of finding filtered doctors started");
        PageDto<DoctorDto> doctors = client.getFilteredDoctors(page, size, specialization);
        log.info("Process of finding filtered doctors ended");
        return doctors;
    }
}
