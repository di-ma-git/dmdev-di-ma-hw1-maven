package com.dima.dto;

import java.util.List;
import lombok.Value;
import org.springframework.data.domain.Page;

@Value
public class PageableResponse<T> {

    List<T> content;
    Metadata metadata;

    public static <T> PageableResponse<T> of(Page<T> page) {
        var metadata = new Metadata(page.getNumber(), page.getSize(), page.getTotalElements());

        return new PageableResponse<>(page.getContent(), metadata);

    }

    @Value
    public static class Metadata {
        int page;
        int size;
        long totalElements;
    }
}
