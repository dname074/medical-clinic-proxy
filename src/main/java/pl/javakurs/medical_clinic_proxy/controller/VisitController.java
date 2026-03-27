package pl.javakurs.medical_clinic_proxy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.javakurs.medical_clinic_proxy.dto.PageDto;
import pl.javakurs.medical_clinic_proxy.dto.VisitDto;
import pl.javakurs.medical_clinic_proxy.model.Specialization;
import pl.javakurs.medical_clinic_proxy.service.VisitService;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/visits")
public class VisitController {
    private final VisitService service;

    @GetMapping("/patients/{id}")
    public PageDto<VisitDto> getPatientVisits(@PathVariable Long id, @RequestParam Integer page, @RequestParam Integer size) {
        log.info("Received GET /visits/patients/{} request with params page = {} and size = {}", id, page, size);
        return service.getPatientVisits(id, page, size);
    }

    @GetMapping("/doctors/{id}")
    public PageDto<VisitDto> getFreeDoctorVisits(@PathVariable Long id, @RequestParam Integer page, @RequestParam Integer size) {
        log.info("Received GET /visits/doctors/{} request with params page = {} and size = {}", id, page, size);
        return service.getFreeDoctorVisits(id, page, size);
    }

    @GetMapping("/doctors")
    public PageDto<VisitDto> getVisitsByDateAndSpecialization(@RequestParam Specialization specialization,
                                                              @RequestParam LocalDate date,
                                                              @RequestParam Integer page,
                                                              @RequestParam Integer size) {
        log.info("Received GET /visits/doctors request with params date = {}, specialization = {}, page = {} and size = {}", date, specialization, page, size);
        return service.getVisitsByDateAndSpecialization(specialization, date, page, size);
    }

    @PatchMapping("/{visitId}/patients/{patientId}")
    public VisitDto assignPatientToVisit(@PathVariable Long visitId, @PathVariable Long patientId) {
        log.info("Received PATCH /visits/{}/patients/{} request", visitId, patientId);
        return service.assignPatientToVisit(visitId, patientId);
    }
}
