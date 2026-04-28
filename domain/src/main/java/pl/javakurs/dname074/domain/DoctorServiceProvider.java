package pl.javakurs.dname074.domain;

import pl.javakurs.model.Doctor;
import pl.javakurs.model.Page;
import pl.javakurs.model.Specialization;

public interface DoctorServiceProvider {
    Page<Doctor> getFilteredDoctors(Integer page, Integer size, Specialization specialization);
}
