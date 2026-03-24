package pl.javakurs.medical_clinic_proxy.dto;

import pl.javakurs.medical_clinic_proxy.model.Specialization;

public record DoctorDto(
        String firstName,
        String lastName,
        Specialization specialization
) {
}
