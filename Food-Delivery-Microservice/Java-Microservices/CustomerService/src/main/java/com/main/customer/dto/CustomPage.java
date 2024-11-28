package com.main.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomPage<T> {
    private List<T>content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}
