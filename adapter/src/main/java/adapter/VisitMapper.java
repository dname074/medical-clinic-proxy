package adapter;

import dto.VisitDto;
import dto.VisitForPatientDto;
import model.Visit;
import model.VisitForPatient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitMapper {
    VisitDto toDto(Visit visit);
    Visit toPojo(VisitDto dto);

    VisitForPatientDto toVisitForPatientDto(VisitForPatient visit);
    VisitForPatient toVisitForPatient(VisitForPatientDto dto);
}
