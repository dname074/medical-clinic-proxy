package pl.javakurs.medical_clinic_proxy.dto;

import java.util.List;

public record PageDto<T>(
        List<T> content,
        int totalPages,
        int pageNumber,
        int pageSize
) {
}
