package pl.javakurs.medical_clinic_proxy.dto;

import pl.javakurs.medical_clinic_proxy.model.Specialization;

public record DoctorDto(
        UserDto user,
        Specialization specialization
) {
}
