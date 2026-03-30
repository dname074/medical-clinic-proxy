package pl.javakurs.medical_clinic_proxy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.javakurs.medical_clinic_proxy.dto.ExceptionDto;
import pl.javakurs.medical_clinic_proxy.dto.FilteredVisitsRequest;
import pl.javakurs.medical_clinic_proxy.dto.PageDto;
import pl.javakurs.medical_clinic_proxy.dto.ValidationExceptionDto;
import pl.javakurs.medical_clinic_proxy.dto.VisitDto;
import pl.javakurs.medical_clinic_proxy.dto.VisitForPatientDto;
import pl.javakurs.medical_clinic_proxy.model.Status;
import pl.javakurs.medical_clinic_proxy.service.VisitService;

@Slf4j
@RequiredArgsConstructor
@RestController
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
                            })
            }
    )
    @GetMapping("/patient/{id}/visits")
    public PageDto<VisitDto> getPatientVisits(@PathVariable @NotNull Long id,
                                              @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        log.info("Received GET /visits/patients/{} request with params page = {} and size = {}", id, page, size);
        return service.getPatientVisits(id, page, size);
    }

    @Operation(summary = "Get doctor's visits")
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
                            })
            }
    )
    @GetMapping("/doctor/{id}/visits")
    public PageDto<VisitDto> getDoctorVisits(@PathVariable @NotNull Long id, @RequestParam(defaultValue = "FREE") Status status,
                                             @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        log.info("Received GET /visits/doctors/{} request with params status = {}, page = {} and size = {}", id, status, page, size);
        return service.getDoctorVisits(id, status, page, size);
    }

    @Operation(summary = "Get visits filtered by date and specialization")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Visits with specified date range and specialization returned",
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
    @GetMapping("/patient/visits")
    public PageDto<VisitForPatientDto> getFilteredVisits(@Valid FilteredVisitsRequest request) {
        log.info("Received GET /visits/doctors request with params fromDate = {}, toDate = {}, specialization = {}, status = {}, page = {} and size = {}",
                request.getFrom(), request.getTo(), request.getSpecialization(), request.getStatus(), request.getPage(), request.getSize());
        if (request.getFrom() != null && request.getTo() != null) {
            return service.getFilteredVisits(request.getSpecialization(), request.getFrom(), request.getTo(),
                    request.getStatus(), request.getPage(), request.getSize());
        }
        return service.getFilteredVisits(request.getSpecialization(), request.getDate(), request.getDate(),
                request.getStatus(), request.getPage(), request.getSize());
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
    @PatchMapping("/patient/{patientId}/visits/{visitId}")
    public VisitDto assignPatientToVisit(@PathVariable @NotNull Long visitId, @PathVariable @NotNull Long patientId) {
        log.info("Received PATCH /visits/{}/patients/{} request", visitId, patientId);
        return service.assignPatientToVisit(visitId, patientId);
    }

    @Operation(summary = "Cancel visit")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Visit canceled",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = VisitDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Wrong data format",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ValidationExceptionDto.class))
                            }),
                    @ApiResponse(responseCode = "404", description = "Visit not found",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ExceptionDto.class))
                            }),
                    @ApiResponse(responseCode = "409", description = "Visit already canceled",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ExceptionDto.class))
                            })
            }
    )
    @PatchMapping("/doctor/visits/{id}")
    public VisitDto cancelVisit(@PathVariable @NotNull Long id) {
        log.info("Received PATCH /visits/{} request", id);
        return service.cancelVisit(id);
    }
}
