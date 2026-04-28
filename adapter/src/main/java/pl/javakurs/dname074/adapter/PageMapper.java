package pl.javakurs.dname074.adapter;

import pl.javakurs.dname074.dto.PageDto;
import pl.javakurs.model.Page;

import java.util.function.Function;

public interface PageMapper {
    <T,R> PageDto<R> toDto(Page<T> page, Function<T, R> mapper);
    <T,R> Page<R> toPojo(PageDto<T> pageDto, Function<T, R> mapper);
}
