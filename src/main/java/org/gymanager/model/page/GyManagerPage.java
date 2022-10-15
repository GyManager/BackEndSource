package org.gymanager.model.page;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
public class GyManagerPage<T> {

    private List<T> content;
    private Boolean last;
    private Boolean first;
    private Boolean empty;
    private Integer pageSize;
    private Integer pageNumber;
    private Long totalElements;
    private Integer totalPages;

    public GyManagerPage(List<T> content, Boolean last, Boolean first, Boolean empty, Integer pageSize,
                         Integer pageNumber, Long totalElements, Integer totalPages) {
        this.content = content;
        this.last = last;
        this.first = first;
        this.empty = empty;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public GyManagerPage(Page<T> page) {
        this(page.getContent(), page.isLast(), page.isFirst(), page.isEmpty(), page.getSize(), page.getNumber(),
                page.getTotalElements(), page.getTotalPages());
    }

    public GyManagerPage(List<T> list) {
        this(new PageImpl<>(list));
    }

    public GyManagerPage(T element) {
        this(List.of(element));
    }

    public <U> GyManagerPage<U> map(Function<? super T, ? extends U> converter){
        return new GyManagerPage<U>(getConvertedContent(converter), last, first, empty, pageSize, pageNumber,
                totalElements, totalPages);
    }

    private <U> List<U> getConvertedContent(Function<? super T,? extends U> converter) {
        return content.stream().map(converter).collect(Collectors.toList());
    }
}
