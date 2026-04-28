package pl.javakurs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Page<T> {
    private List<T> content;
    private int totalPages;
    private int pageNumber;
    private int pageSize;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Page<?> page = (Page<?>) o;
        return totalPages == page.totalPages && pageNumber == page.pageNumber && pageSize == page.pageSize && Objects.equals(content, page.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, totalPages, pageNumber, pageSize);
    }

    @Override
    public String toString() {
        return "Page{" +
                "content=" + content +
                ", totalPages=" + totalPages +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                '}';
    }
}
