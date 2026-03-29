package pl.javakurs.medical_clinic_proxy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.javakurs.medical_clinic_proxy.client.MedicalClinicClient;
import pl.javakurs.medical_clinic_proxy.dto.PageDto;
import pl.javakurs.medical_clinic_proxy.dto.VisitDto;
import pl.javakurs.medical_clinic_proxy.exception.badrequest.BeforeCurrentDateException;
import pl.javakurs.medical_clinic_proxy.model.Specialization;

import java.time.Clock;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisitService {
    private final MedicalClinicClient client;
    private final Clock clock;

    public PageDto<VisitDto> getPatientVisits(Long patientId, Integer page, Integer size) {
        log.info("Process of receiving patient's visits started");
        PageDto<VisitDto> visitsPage = client.getPatientVisits(patientId, page, size);
        log.info("Process of receiving patient's visits ended");
        return visitsPage;
    }

    public PageDto<VisitDto> getFreeDoctorVisits(Long doctorId, Integer page, Integer size) {
        log.info("Process of receiving doctor's free visits started");
        PageDto<VisitDto> visitsPage = client.getFreeDoctorVisits(doctorId, page, size);
        log.info("Process of receiving doctor's free visits ended");
        return visitsPage;
    }

    public PageDto<VisitDto> getVisitsByDateAndSpecialization(Specialization specialization,
                                                              LocalDate date,
                                                              Integer page,
                                                              Integer size) {
        log.info("Process of receiving free visits by specialization and date started");
        if (date.isBefore(LocalDate.now(clock))) {
            throw new BeforeCurrentDateException("Past visits are no longer available");
        }
        PageDto<VisitDto> visitsPage = client.getVisitsByDateAndDoctorSpecialization(specialization, date, page, size);
        log.info("Process of receiving free visits by specialization and date ended");
        return visitsPage;
    }

    public VisitDto assignPatientToVisit(Long visitId, Long patientId) {
        log.info("Process of assigning patient to visit started");
        VisitDto visit = client.assignPatientToVisit(visitId, patientId);
        log.info("Process of assigning patient to visit ended");
        return visit;
    }
}
