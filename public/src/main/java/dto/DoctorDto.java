package dto;

import model.Specialization;

public record DoctorDto(
        UserDto user,
        Specialization specialization
) {
}
