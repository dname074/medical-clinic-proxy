package model;

import java.util.List;

public record Page<T>(
        List<T> content,
        int totalPages,
        int pageNumber,
        int pageSize
) {
}
