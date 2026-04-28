package pl.javakurs.dname074.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.javakurs.dname074.domain.MedicalClinicProvider;
import pl.javakurs.model.*;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
class MedicalClinicClientImpl implements MedicalClinicProvider {
    private final MedicalClinicClient client;
    private final DoctorMapper doctorMapper;
    private final VisitMapper visitMapper;
    private final PageMapper pageMapper;

    @Override
    public Page<Doctor> getFilteredDoctors(Integer page, Integer size, Specialization specialization) {
        return pageMapper.toPojo(client.getFilteredDoctors(specialization, page, size), doctorMapper::toPojo);
    }

    @Override
    public Page<Visit> getPatientVisits(Long id, Integer page, Integer size) {
        return pageMapper.toPojo(client.getPatientVisits(id, page, size), visitMapper::toPojo);
    }

    @Override
    public Page<Visit> getDoctorVisits(Long id, VisitStatus status, Integer page, Integer size) {
        return pageMapper.toPojo(client.getDoctorVisits(id, status, page, size), visitMapper::toPojo);
    }

    @Override
    public Visit assignPatientToVisit(Long visitId, Long patientId) {
        return visitMapper.toPojo(client.assignPatientToVisit(visitId, patientId));
    }

    @Override
    public Visit cancelVisit(Long id) {
        return visitMapper.toPojo(client.cancelVisit(id));
    }

    @Override
    public Page<VisitForPatient> getFilteredVisits(Specialization specialization, LocalDate from, LocalDate to,
                                                   VisitStatus status, Integer page, Integer size) {
        return pageMapper.toPojo(client.getFilteredVisits(specialization, from, to, status, page, size),
                visitMapper::toVisitForPatient);
    }
}
