package pl.javakurs.medical_clinic_proxy.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pl.javakurs.medical_clinic_proxy.client.configuration.MedicalClinicClientConfiguration;
import pl.javakurs.medical_clinic_proxy.dto.DoctorDto;
import pl.javakurs.medical_clinic_proxy.dto.PageDto;
import pl.javakurs.medical_clinic_proxy.dto.VisitDto;
import pl.javakurs.medical_clinic_proxy.dto.VisitForPatientDto;
import pl.javakurs.medical_clinic_proxy.model.Specialization;
import pl.javakurs.medical_clinic_proxy.model.VisitAvailability;

import java.time.LocalDate;

@FeignClient(
        name = "medicalclinicClient",
        configuration = MedicalClinicClientConfiguration.class
)
public interface MedicalClinicClient {
    @GetMapping("/visits/patients/{id}")
    PageDto<VisitDto> getPatientVisits(@PathVariable Long id,
                                       @RequestParam Integer page, @RequestParam Integer size);
    @GetMapping("/visits/doctors/{id}")
    PageDto<VisitDto> getDoctorVisits(@PathVariable Long id, @RequestParam VisitAvailability availability,
                                      @RequestParam Integer page, @RequestParam Integer size);
    @GetMapping("/visits/doctors")
    PageDto<VisitForPatientDto> getFilteredVisits(@RequestParam(required = false) Specialization specialization,
                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                                                  @RequestParam VisitAvailability availability,
                                                  @RequestParam Integer page,
                                                  @RequestParam Integer size);
    @GetMapping("/doctors")
    PageDto<DoctorDto> getFilteredDoctors(@RequestParam(required = false) Specialization specialization,
                                          @RequestParam Integer page, @RequestParam Integer size);
    @PatchMapping("/visits/{visitId}/patients/{patientId}")
    VisitDto assignPatientToVisit(@PathVariable Long visitId, @PathVariable Long patientId);
    @PatchMapping("/visits/{id}")
    VisitDto cancelVisit(@PathVariable Long id);
}
