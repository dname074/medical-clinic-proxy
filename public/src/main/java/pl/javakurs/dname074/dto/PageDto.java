package pl.javakurs.dname074.dto;

import java.util.List;

public record PageDto<T>(
        List<T> content,
        int totalPages,
        int pageNumber,
        int pageSize
) {
}
