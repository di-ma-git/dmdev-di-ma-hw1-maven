package com.dima.dto;

import java.util.List;
import lombok.Value;

@Value
public class PageResponse<T> {

    List<T> content;
    Metadata metadata;

    @Value
    public static class Metadata {
        int page;
        int size;
        long totalElements;
    }

}
