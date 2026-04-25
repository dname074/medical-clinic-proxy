package model;

import java.time.LocalDate;

public record Patient(
        String email,
        String idCardNo,
        String phoneNumber,
        LocalDate birthday,
        User user
        ) {
}
