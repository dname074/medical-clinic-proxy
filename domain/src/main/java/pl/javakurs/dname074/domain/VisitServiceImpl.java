package pl.javakurs.dname074.domain;

import pl.javakurs.model.*;
import pl.javakurs.model.exception.BeforeCurrentDateException;
import pl.javakurs.model.exception.WrongDateOrderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Clock;
import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitServiceProvider {
    private final MedicalClinicProvider client;
    private final Clock clock;

    public Page<Visit> getPatientVisits(Long patientId, Integer page, Integer size) {
        log.info("Process of receiving patient's visits started");
        Page<Visit> visitsPage = client.getPatientVisits(patientId, page, size);
        log.info("Process of receiving patient's visits ended");
        return visitsPage;
    }

    public Page<Visit> getDoctorVisits(Long doctorId, VisitStatus status, Integer page, Integer size) {
        log.info("Process of receiving doctor's visits started");
        Page<Visit> visitsPage = client.getDoctorVisits(doctorId, status, page, size);
        log.info("Process of receiving doctor's visits ended");
        return visitsPage;
    }

    public Page<VisitForPatient> getFilteredVisits(Specialization specialization, LocalDate date, LocalDate from, LocalDate to,
                                                   VisitStatus status, Integer page, Integer size) {
        log.info("Process of receiving free visits by specialization and date started");
        Page<VisitForPatient> visitsPage = (date != null)
                ? getVisitsAndValidate(specialization, date, date, status, page, size)
                : getVisitsAndValidate(specialization, from, to, status, page, size);
        log.info("Process of receiving free visits by specialization and date ended");
        return visitsPage;
    }

    public Visit assignPatientToVisit(Long visitId, Long patientId) {
        log.info("Process of assigning patient to visit started");
        Visit visit = client.assignPatientToVisit(visitId, patientId);
        log.info("Process of assigning patient to visit ended");
        return visit;
    }

    public Visit cancelVisit(Long id) {
        log.info("Process of canceling visit started");
        Visit visit = client.cancelVisit(id);
        log.info("Process of canceling visit ended");
        return visit;
    }

    private Page<VisitForPatient> getVisitsAndValidate(Specialization specialization, LocalDate from, LocalDate to,
                                                       VisitStatus status, Integer page, Integer size) {
        validateVisit(from, to);
        return client.getFilteredVisits(specialization, from, to, status, page, size);
    }

    private void validateVisit(LocalDate fromDate, LocalDate toDate) {
        if (fromDate.isBefore(LocalDate.now(clock))) {
            throw new BeforeCurrentDateException("Past visits are no longer available");
        }
        if (fromDate.equals(toDate)) {
            return;
        }
        if (fromDate.isAfter(toDate)) {
            throw new WrongDateOrderException("First date must be before second date");
        }
    }
}
