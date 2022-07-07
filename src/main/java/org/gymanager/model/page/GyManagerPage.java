package org.gymanager.model.page;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

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

    public GyManagerPage(Page<T> page) {
        this.content = page.getContent();
        this.last = page.isLast();
        this.first = page.isFirst();
        this.empty = page.isEmpty();
        this.pageSize = page.getSize();
        this.pageNumber = page.getNumber();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }

    public GyManagerPage(List<T> list) {
        this(new PageImpl<>(list));
    }

    public GyManagerPage(T element) {
        this(List.of(element));
    }
}
