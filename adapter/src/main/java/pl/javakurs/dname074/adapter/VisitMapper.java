package pl.javakurs.dname074.adapter;

import pl.javakurs.dname074.dto.VisitDto;
import pl.javakurs.dname074.dto.VisitForPatientDto;
import org.mapstruct.Mapper;
import pl.javakurs.model.Visit;
import pl.javakurs.model.VisitForPatient;

@Mapper(componentModel = "spring")
interface VisitMapper {
    VisitDto toDto(Visit visit);
    Visit toPojo(VisitDto dto);
    VisitForPatientDto toVisitForPatientDto(VisitForPatient visit);
    VisitForPatient toVisitForPatient(VisitForPatientDto dto);
}
