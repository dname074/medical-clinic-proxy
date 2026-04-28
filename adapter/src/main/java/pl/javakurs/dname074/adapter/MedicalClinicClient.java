package pl.javakurs.dname074.adapter;

import pl.javakurs.dname074.adapter.client.configuration.MedicalClinicClientConfiguration;
import pl.javakurs.dname074.dto.DoctorDto;
import pl.javakurs.dname074.dto.PageDto;
import pl.javakurs.dname074.dto.VisitDto;
import pl.javakurs.dname074.dto.VisitForPatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pl.javakurs.model.Specialization;
import pl.javakurs.model.VisitStatus;

import java.time.LocalDate;

@FeignClient(
        name = "medicalclinicClient",
        configuration = MedicalClinicClientConfiguration.class
)
interface MedicalClinicClient {
    @GetMapping("/visits/patients/{id}")
    PageDto<VisitDto> getPatientVisits(@PathVariable Long id,
                                       @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size);
    @GetMapping("/visits/doctors/{id}")
    PageDto<VisitDto> getDoctorVisits(@PathVariable Long id, @RequestParam(required = false) VisitStatus status,
                                      @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size);
    @GetMapping("/visits")
    PageDto<VisitForPatientDto> getFilteredVisits(@RequestParam(required = false) Specialization specialization,
                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                                                  @RequestParam(required = false) VisitStatus status,
                                                  @RequestParam(required = false) Integer page,
                                                  @RequestParam(required = false) Integer size);
    @GetMapping("/doctors")
    PageDto<DoctorDto> getFilteredDoctors(@RequestParam(required = false) Specialization specialization,
                                          @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size);
    @PatchMapping("/visits/{visitId}/patients/{patientId}")
    VisitDto assignPatientToVisit(@PathVariable Long visitId, @PathVariable Long patientId);
    @PatchMapping("/visits/{id}")
    VisitDto cancelVisit(@PathVariable Long id);
}
