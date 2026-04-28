package pl.javakurs.dname074.dto;

import pl.javakurs.model.Specialization;

public record DoctorDto(
        UserDto user,
        Specialization specialization
) {
}
