package dto;

import java.time.LocalDate;

public record PatientDto(
        String email,
        String idCardNo,
        String phoneNumber,
        LocalDate birthday,
        UserDto user
        ) {
}
