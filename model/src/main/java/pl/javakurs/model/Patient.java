package pl.javakurs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Patient {
    private String email;
    private String idCardNo;
    private String phoneNumber;
    private LocalDate birthday;
    private User user;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(email, patient.email) && Objects.equals(idCardNo, patient.idCardNo) && Objects.equals(phoneNumber, patient.phoneNumber) && Objects.equals(birthday, patient.birthday) && Objects.equals(user, patient.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, idCardNo, phoneNumber, birthday, user);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "email='" + email + '\'' +
                ", idCardNo='" + idCardNo + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthday=" + birthday +
                ", user=" + user +
                '}';
    }
}
