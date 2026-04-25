package domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Doctor;
import model.Page;
import model.Specialization;

@Slf4j
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorServiceProvider {
    private final MedicalClinicClientProvider client;

    public Page<Doctor> getFilteredDoctors(Integer page, Integer size, Specialization specialization) {
        log.info("Process of finding filtered doctors started");
        Page<Doctor> doctors = client.getFilteredDoctors(page, size, specialization);
        log.info("Process of finding filtered doctors ended");
        return doctors;
    }
}
