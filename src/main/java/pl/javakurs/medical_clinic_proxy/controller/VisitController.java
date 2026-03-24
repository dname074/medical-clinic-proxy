package pl.javakurs.medical_clinic_proxy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.javakurs.medical_clinic_proxy.dto.PageDto;
import pl.javakurs.medical_clinic_proxy.dto.VisitDto;
import pl.javakurs.medical_clinic_proxy.service.VisitService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/visits")
public class VisitController {
    private final VisitService service;

    @GetMapping("/patients/{id}")
    public PageDto<VisitDto> getPatientVisits(@PathVariable Long id, @RequestParam Integer page, @RequestParam Integer size) {
        return service.getPatientVisits(id, page, size);
    }
}
