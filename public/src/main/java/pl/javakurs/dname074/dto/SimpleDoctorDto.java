package pl.javakurs.dname074.dto;

import pl.javakurs.model.Specialization;

public record SimpleDoctorDto(
        SimpleUserDto user,
        Specialization specialization
) {
}
