package pl.javakurs.medical_clinic_proxy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.javakurs.medical_clinic_proxy.client.MedicalClinicClient;
import pl.javakurs.medical_clinic_proxy.dto.PageDto;
import pl.javakurs.medical_clinic_proxy.dto.VisitDto;
import pl.javakurs.medical_clinic_proxy.exception.badrequest.BeforeCurrentDateException;
import pl.javakurs.medical_clinic_proxy.model.Specialization;

import java.time.Clock;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class VisitService {
    private final MedicalClinicClient client;
    private final Clock clock;

    public PageDto<VisitDto> getPatientVisits(Long patientId, Integer page, Integer size) {
        return client.getPatientVisits(patientId, page, size);
    }

    public PageDto<VisitDto> getFreeDoctorVisits(Long doctorId, Integer page, Integer size) {
            return client.getFreeDoctorVisits(doctorId, page, size);
    }

    public PageDto<VisitDto> getVisitsByDateAndSpecialization(Specialization specialization,
                                                              LocalDate date,
                                                              Integer page,
                                                              Integer size) {
        if (date.isBefore(LocalDate.now(clock))) {
            throw new BeforeCurrentDateException("Past visits are no longer available");
        }
        return client.getVisitsByDateAndDoctorSpecialization(specialization, date, page, size);
    }

    public VisitDto assignPatientToVisit(Long visitId, Long patientId) {
        return client.assignPatientToVisit(visitId, patientId);
    }
}
