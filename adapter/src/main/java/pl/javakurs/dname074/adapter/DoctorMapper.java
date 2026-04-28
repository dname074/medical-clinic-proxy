package pl.javakurs.dname074.adapter;

import pl.javakurs.dname074.dto.DoctorDto;
import org.mapstruct.Mapper;
import pl.javakurs.model.Doctor;

@Mapper(componentModel = "spring")
interface DoctorMapper {
    DoctorDto toDto(Doctor doctor);
    Doctor toPojo(DoctorDto doctorDto);
}
