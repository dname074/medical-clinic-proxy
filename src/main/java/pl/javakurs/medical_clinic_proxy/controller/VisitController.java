package pl.javakurs.medical_clinic_proxy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.javakurs.medical_clinic_proxy.dto.ExceptionDto;
import pl.javakurs.medical_clinic_proxy.dto.PageDto;
import pl.javakurs.medical_clinic_proxy.dto.ValidationExceptionDto;
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

    @Operation(summary = "Get patient's visits")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Patient's visits returned",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = PageDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Wrong data format passed",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ValidationExceptionDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Missing parameter",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ExceptionDto.class))
                            })
            }
    )
    @GetMapping("/patients/{id}")
    public PageDto<VisitDto> getPatientVisits(@PathVariable @NotNull Long id, @RequestParam Integer page, @RequestParam Integer size) {
        log.info("Received GET /visits/patients/{} request with params page = {} and size = {}", id, page, size);
        return service.getPatientVisits(id, page, size);
    }

    @Operation(summary = "Get doctor's free visits")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Free doctor's visits returned",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = PageDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Wrong data format passed",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ValidationExceptionDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Missing parameter",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ExceptionDto.class))
                            })
            }
    )
    @GetMapping("/doctors/{id}")
    public PageDto<VisitDto> getFreeDoctorVisits(@PathVariable @NotNull Long id, @RequestParam Integer page, @RequestParam Integer size) {
        log.info("Received GET /visits/doctors/{} request with params page = {} and size = {}", id, page, size);
        return service.getFreeDoctorVisits(id, page, size);
    }

    @Operation(summary = "Get visits filtered by date and specialization")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Visits with specified date and specialization returned",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = PageDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Wrong data format passed",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ValidationExceptionDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Missing parameter",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ExceptionDto.class))
                            })
            }
    )
    @GetMapping("/doctors")
    public PageDto<VisitDto> getVisitsByDateAndSpecialization(@RequestParam Specialization specialization,
                                                              @RequestParam @Future LocalDate date,
                                                              @RequestParam Integer page,
                                                              @RequestParam Integer size) {
        log.info("Received GET /visits/doctors request with params date = {}, specialization = {}, page = {} and size = {}", date, specialization, page, size);
        return service.getVisitsByDateAndSpecialization(specialization, date, page, size);
    }

    @Operation(summary = "Assign patient to visit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient assigned to visit",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = VisitDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Wrong data format passed",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ValidationExceptionDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Patient not found or visit not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    }),
            @ApiResponse(responseCode = "409", description = "Visit already taken",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    })
    })
    @PatchMapping("/{visitId}/patients/{patientId}")
    public VisitDto assignPatientToVisit(@PathVariable @NotNull Long visitId, @PathVariable @NotNull Long patientId) {
        log.info("Received PATCH /visits/{}/patients/{} request", visitId, patientId);
        return service.assignPatientToVisit(visitId, patientId);
    }
}
