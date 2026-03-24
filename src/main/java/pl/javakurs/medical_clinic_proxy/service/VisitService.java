package pl.javakurs.medical_clinic_proxy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.javakurs.medical_clinic_proxy.client.MedicalClinicClient;
import pl.javakurs.medical_clinic_proxy.dto.PageDto;
import pl.javakurs.medical_clinic_proxy.dto.VisitDto;

@Service
@RequiredArgsConstructor
public class VisitService {
    private final MedicalClinicClient client;

    public PageDto<VisitDto> getPatientVisits(Long id, Integer page, Integer size) {
        return client.getPatientVisits(id, page, size);
    }
}
