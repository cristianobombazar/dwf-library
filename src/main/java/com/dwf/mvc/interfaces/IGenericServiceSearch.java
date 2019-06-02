package com.dwf.mvc.interfaces;

import com.dwf.data.SearchParameters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IGenericServiceSearch<E> {
    Page<E> findAll(List<SearchParameters> parameters, Pageable pageable);
}
