package pl.javakurs.medical_clinic_proxy.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pl.javakurs.medical_clinic_proxy.client.configuration.MedicalClinicClientConfiguration;
import pl.javakurs.medical_clinic_proxy.dto.PageDto;
import pl.javakurs.medical_clinic_proxy.dto.VisitDto;

@FeignClient(
        name = "medicalclinicClient",
        configuration = MedicalClinicClientConfiguration.class
)
public interface MedicalClinicClient {
    @GetMapping("/visits/patients/{id}")
    PageDto<VisitDto> getPatientVisits(@PathVariable Long id, @RequestParam Integer page, @RequestParam Integer size);
    @PatchMapping("/visits/{visitId}/patients/{patientId}")
    VisitDto assignPatientToVisit(@PathVariable Long visitId, @PathVariable Long patientId);
}
