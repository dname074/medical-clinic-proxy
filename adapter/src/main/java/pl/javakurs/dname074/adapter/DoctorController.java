package pl.javakurs.dname074.adapter;

import pl.javakurs.dname074.dto.DoctorDto;
import pl.javakurs.dname074.dto.PageDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.javakurs.dname074.domain.DoctorServiceProvider;
import pl.javakurs.model.Specialization;

@Slf4j
@RestController
@RequiredArgsConstructor
class DoctorController {
    private final DoctorServiceProvider service;
    private final DoctorMapper doctorMapper;
    private final PageMapper pageMapper;

    @Operation(summary = "Get all doctors in page based on request params")
    @GetMapping("/patient/doctors")
    public PageDto<DoctorDto> getFilteredDoctors(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
                                                 @RequestParam(required = false) Specialization specialization) {
        log.info("Received GET /doctors request with parameters: page={}, size={}, specialization={}", page, size, specialization);
        return pageMapper.toDto(service.getFilteredDoctors(page, size, specialization), doctorMapper::toDto);
    }
}
