package com.example.recruitment.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageDtoOut<T> {
    private Integer page;
    private Integer pageSize;
    private Long totalElements;
    private Long totalPages;
    private List<T> data = new ArrayList<>();

    public static <T> PageDtoOut<T> from(Integer page, Integer pageSize, Long totalElements, List<T> data) {
        Long totalPages = totalElements / pageSize;
        if (totalElements % pageSize != 0) {
            totalPages++;
        }
        return PageDtoOut.<T>builder()
                .page(page)
                .pageSize(pageSize)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .data(data)
                .build();
    }
}
