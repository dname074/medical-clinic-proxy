package pl.javakurs.dname074.domain;

import pl.javakurs.model.*;

import java.time.LocalDate;

public interface MedicalClinicProvider {
    Page<Doctor> getFilteredDoctors(Integer page, Integer size, Specialization specialization);

    Page<Visit> getPatientVisits(Long patientId, Integer page, Integer size);

    Page<Visit> getDoctorVisits(Long doctorId, VisitStatus status, Integer page, Integer size);

    Visit assignPatientToVisit(Long visitId, Long patientId);

    Visit cancelVisit(Long id);

    Page<VisitForPatient> getFilteredVisits(Specialization specialization, LocalDate from, LocalDate to, VisitStatus status, Integer page, Integer size);
}
