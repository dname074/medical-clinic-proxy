package pl.javakurs.medical_clinic_proxy.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.javakurs.medical_clinic_proxy.dto.DoctorDto;
import pl.javakurs.medical_clinic_proxy.dto.PageDto;
import pl.javakurs.medical_clinic_proxy.model.Specialization;
import pl.javakurs.medical_clinic_proxy.service.DoctorService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService service;

    @Operation(summary = "Get all doctors in page based on request params")
    @GetMapping("/patient/doctors")
    public PageDto<DoctorDto> getFilteredDoctors(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
                                                 @RequestParam(required = false) Specialization specialization) {
        log.info("Received GET /doctors request with parameters: page={}, size={}, specialization={}", page, size, specialization);
        return service.getFilteredDoctors(page, size, specialization);
    }
}
