package domain;

import model.Doctor;
import model.Page;
import model.Specialization;

public interface DoctorServiceProvider {
    Page<Doctor> getFilteredDoctors(Integer page, Integer size, Specialization specialization);
}
