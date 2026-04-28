package pl.javakurs.dname074.adapter;

import pl.javakurs.dname074.dto.DoctorDto;
import pl.javakurs.dname074.dto.SimpleDoctorDto;
import pl.javakurs.dname074.dto.SimpleUserDto;
import pl.javakurs.dname074.dto.UserDto;
import pl.javakurs.model.*;

public class DoctorTestDataFactory {
    public static DoctorDto createDoctorDto() {
        return new DoctorDto(new UserDto("Jan", "Kowalski"), Specialization.DERMATOLOGIST);
    }

    public static Doctor createDoctor() {
        return new Doctor(new User("Jan", "Kowalski"), Specialization.DERMATOLOGIST);
    }

    public static SimpleDoctorDto createSimpleDoctor() {
        return new SimpleDoctorDto(new SimpleUserDto("Jan", "Kowalski"), Specialization.DERMATOLOGIST);
    }
}
