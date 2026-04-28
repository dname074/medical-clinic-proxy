package pl.javakurs.model;

import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Doctor {
    private User user;
    private Specialization specialization;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(user, doctor.user) && specialization == doctor.specialization;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, specialization);
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "user=" + user +
                ", specialization=" + specialization +
                '}';
    }
}
