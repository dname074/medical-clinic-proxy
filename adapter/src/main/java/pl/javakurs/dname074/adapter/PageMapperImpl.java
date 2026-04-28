package pl.javakurs.dname074.adapter;

import pl.javakurs.dname074.dto.PageDto;
import pl.javakurs.model.Page;

import java.util.function.Function;
import java.util.stream.Collectors;

class PageMapperImpl implements PageMapper {
    @Override
    public <T,R> PageDto<R> toDto(Page<T> page, Function<T, R> mapper) {
        return new PageDto<>(
            page.getContent().stream().map(mapper).collect(Collectors.toList()),
            page.getTotalPages(),
            page.getPageNumber(),
            page.getPageSize()
        );
    }

    @Override
    public <T,R> Page<R> toPojo(PageDto<T> pageDto, Function<T, R> mapper) {
        return new Page<>(
                pageDto.content().stream().map(mapper).collect(Collectors.toList()),
                pageDto.totalPages(),
                pageDto.pageNumber(),
                pageDto.pageSize()
        );
    }
}
