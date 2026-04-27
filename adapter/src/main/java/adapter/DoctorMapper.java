package adapter;

import dto.DoctorDto;
import model.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface DoctorMapper {
    DoctorDto toDto(Doctor doctor);
    Doctor toPojo(DoctorDto doctorDto);
}
