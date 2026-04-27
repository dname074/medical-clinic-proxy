package adapter;

import dto.PageDto;
import model.Page;

import java.util.function.Function;
import java.util.stream.Collectors;

class PageMapper {
    <T,R> PageDto<R> toDto(Page<T> page, Function<T, R> mapper) {
        return new PageDto<>(
            page.content().stream().map(mapper).collect(Collectors.toList()),
            page.pageNumber(),
            page.pageSize(),
            page.totalPages()
        );
    }

    <T,R> Page<R> toPojo(PageDto<T> pageDto, Function<T, R> mapper) {
        return new Page<>(
                pageDto.content().stream().map(mapper).collect(Collectors.toList()),
                pageDto.pageNumber(),
                pageDto.pageSize(),
                pageDto.totalPages()
        );
    }
}
