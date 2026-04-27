package adapter;

import domain.VisitServiceProvider;
import dto.ExceptionDto;
import dto.FilteredVisitsRequest;
import dto.PageDto;
import dto.ValidationExceptionDto;
import dto.VisitDto;
import dto.VisitForPatientDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.VisitStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
class VisitController {
    private final VisitServiceProvider service;
    private final PageMapper pageMapper;
    private final VisitMapper visitMapper;

    @Operation(summary = "Get patient's visits")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "model.Patient's visits returned",
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
        return pageMapper.toDto(service.getPatientVisits(id, page, size), visitMapper::toDto);
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
    public PageDto<VisitDto> getDoctorVisits(@PathVariable @NotNull Long id, @RequestParam(required = false) VisitStatus status,
                                             @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        log.info("Received GET /visits/doctors/{} request with params status = {}, page = {} and size = {}", id, status, page, size);
        return pageMapper.toDto(service.getDoctorVisits(id, status, page, size), visitMapper::toDto);
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
        log.info("Received GET /visits/doctors request with params fromDate = {}, toDate = {}, exact date= {}, specialization = {}, status = {}, page = {} and size = {}",
                request.getFrom(), request.getTo(), request.getDate(), request.getSpecialization(), request.getStatus(), request.getPage(), request.getSize());
        return pageMapper.toDto(service.getFilteredVisits(request.getSpecialization(), request.getDate(), request.getFrom(), request.getTo(),
                request.getStatus(), request.getPage(), request.getSize()), visitMapper::toVisitForPatientDto);
    }

    @Operation(summary = "Assign patient to visit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "model.Patient assigned to visit",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = VisitDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Wrong data format passed",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ValidationExceptionDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "model.Patient not found or visit not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    }),
            @ApiResponse(responseCode = "409", description = "model.Visit already taken",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    })
    })
    @PatchMapping("/patient/{patientId}/visits/{visitId}")
    public VisitDto assignPatientToVisit(@PathVariable @NotNull Long visitId, @PathVariable @NotNull Long patientId) {
        log.info("Received PATCH /visits/{}/patients/{} request", visitId, patientId);
        return visitMapper.toDto(service.assignPatientToVisit(visitId, patientId));
    }

    @Operation(summary = "Cancel visit")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "model.Visit canceled",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = VisitDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Wrong data format",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ValidationExceptionDto.class))
                            }),
                    @ApiResponse(responseCode = "404", description = "model.Visit not found",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ExceptionDto.class))
                            }),
                    @ApiResponse(responseCode = "409", description = "model.Visit already canceled",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ExceptionDto.class))
                            })
            }
    )
    @PatchMapping("/doctor/visits/{id}")
    public VisitDto cancelVisit(@PathVariable @NotNull Long id) {
        log.info("Received PATCH /visits/{} request", id);
        return visitMapper.toDto(service.cancelVisit(id));
    }
}
