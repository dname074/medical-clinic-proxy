package pl.javakurs.medical_clinic_proxy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.javakurs.medical_clinic_proxy.client.MedicalClinicClient;
import pl.javakurs.medical_clinic_proxy.dto.PageDto;
import pl.javakurs.medical_clinic_proxy.dto.VisitDto;
import pl.javakurs.medical_clinic_proxy.dto.VisitForPatientDto;
import pl.javakurs.medical_clinic_proxy.exception.badrequest.BeforeCurrentDateException;
import pl.javakurs.medical_clinic_proxy.exception.badrequest.WrongDateOrderException;
import pl.javakurs.medical_clinic_proxy.model.Specialization;
import pl.javakurs.medical_clinic_proxy.model.Status;

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

    public PageDto<VisitDto> getDoctorVisits(Long doctorId, Status status, Integer page, Integer size) {
        log.info("Process of receiving doctor's visits started");
        PageDto<VisitDto> visitsPage = client.getFreeDoctorVisits(doctorId, status, page, size);
        log.info("Process of receiving doctor's visits ended");
        return visitsPage;
    }

    public PageDto<VisitForPatientDto> getFilteredVisits(Specialization specialization,
                                                         LocalDate fromDate, LocalDate toDate,
                                                         Status status, Integer page, Integer size) {
        log.info("Process of receiving free visits by specialization and date started");
        if (fromDate.isBefore(LocalDate.now(clock))) {
            throw new BeforeCurrentDateException("Past visits are no longer available");
        }
        if (fromDate.isAfter(toDate)) {
            throw new WrongDateOrderException("First date must be before second date");
        }
        PageDto<VisitForPatientDto> visitsPage = client.getFilteredVisits(specialization, fromDate, toDate, status, page, size);
        log.info("Process of receiving free visits by specialization and date ended");
        return visitsPage;
    }

    public VisitDto assignPatientToVisit(Long visitId, Long patientId) {
        log.info("Process of assigning patient to visit started");
        VisitDto visit = client.assignPatientToVisit(visitId, patientId);
        log.info("Process of assigning patient to visit ended");
        return visit;
    }

    public VisitDto cancelVisit(Long id) {
        log.info("Process of canceling visit started");
        VisitDto visit = client.cancelVisit(id);
        log.info("Process of canceling visit ended");
        return visit;
    }
}
