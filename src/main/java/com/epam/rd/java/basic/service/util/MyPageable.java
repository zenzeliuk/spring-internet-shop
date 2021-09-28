package com.epam.rd.java.basic.service.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class MyPageable {

    private MyPageable() {
        throw new IllegalStateException("Utility class");
    }

    public static Pageable getPageable(Integer page, Integer size, String sortField, String sortDir) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        return PageRequest.of(page - 1, size, sort);
    }
}
