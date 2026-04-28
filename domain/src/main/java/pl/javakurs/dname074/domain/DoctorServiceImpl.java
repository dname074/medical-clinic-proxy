package pl.javakurs.dname074.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.javakurs.model.Doctor;
import pl.javakurs.model.Page;
import pl.javakurs.model.Specialization;

@Slf4j
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorServiceProvider {
    private final MedicalClinicProvider client;

    public Page<Doctor> getFilteredDoctors(Integer page, Integer size, Specialization specialization) {
        log.info("Process of finding filtered doctors started");
        Page<Doctor> doctors = client.getFilteredDoctors(page, size, specialization);
        log.info("Process of finding filtered doctors ended");
        return doctors;
    }
}
